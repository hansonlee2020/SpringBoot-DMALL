package com.hanson.service.impl;


import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.mapper.MemberMapper;
import com.hanson.pojo.dto.MemberTable;
import com.hanson.pojo.vo.Member;
import com.hanson.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: DreamMall
 * @description: 用户接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-04-21 14:01
 **/
@Service
public class MemberServiceImpl implements MemberService {
    private MemberMapper memberMapper;
    @Autowired
    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public PageResult<List<MemberTable>> splitMember(Integer pageSize, Integer currentPage, String key,Integer state) {
        String newKey;//保存格式key
        int total;
        int pages;
        if (key != null) {
            newKey = key.replaceAll(" ","");
            if ("".equals(newKey)) total = memberMapper.doCounts(state);//输入了空格字符串
            else total = memberMapper.doSearchMember(newKey,state).size();
        }
        else {
            newKey = null;
            total = memberMapper.doCounts(state);
        }
        if (total <= 0) throw new NullPointerException("暂无匹配数据");
        Integer startIndex = (currentPage - 1) * pageSize;
        List<Member> members = memberMapper.doSplitMember(startIndex, pageSize, newKey,state);
        if (total % pageSize != 0)pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
        else pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
        List<MemberTable> memberTables = new ArrayList<>();//转换为前端用户表类
        for (Member member : members) {
            MemberTable memberTable = new MemberTable(member, Constant.Y_M_D_H_M_S);//Y_M_D_H_M_S：日期格式
            memberTables.add(memberTable);
        }
        return new PageResult<>(ResultCode.SUCCESS,total,memberTables);
    }

    @Override
    public PageResult<List<MemberTable>> splitMemberTrash(Integer pageSize, Integer currentPage, String key,Integer state) {
        String newKey;//保存格式key
        int total;
        if (key != null) {
            newKey = key.replaceAll(" ","");
            if ("".equals(newKey)) total = memberMapper.doTrashCount(state);//输入了空格字符串
            else total = memberMapper.doSearchMemberTrash(newKey,state).size();
        }else {
            newKey = null;
            total = memberMapper.doTrashCount(state);
        }
        if (total <= 0) throw new NullPointerException("暂无匹配的数据");
        Integer startIndex = (currentPage - 1) * pageSize;
        List<Member> members = memberMapper.doSplitMemberTrash(startIndex, pageSize, newKey,state);
        List<MemberTable> memberTables = new ArrayList<>();//转换为前端用户表类
        for (Member member : members) {
            MemberTable memberTable = new MemberTable(member,Constant.Y_M_D_H_M_S);
            memberTables.add(memberTable);
        }
        return new PageResult<>(ResultCode.SUCCESS,total,memberTables);
    }

    @Override
    public CommonResult<Integer> updateMemberState(Integer memberId, Integer state) {
        Integer num = memberMapper.doUpdateMemberState(memberId, state);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    public CommonResult<Integer> deleteMember(Integer memberId) {
        Integer num = memberMapper.doDeleteMember(memberId);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    public CommonResult<Integer> batchDeleteMembers(List<Integer> ids) {
        Set<Integer> newIds = new HashSet<>(ids);
        Integer num = memberMapper.doBatchDeleteMembers(newIds);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    public CommonResult<Integer> batchUpdateMembers(List<Integer> ids, Integer state) {
        Set<Integer> newIds = new HashSet<>(ids);
        Integer num = memberMapper.doBatchUpdateMembers(newIds,state);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

}
