package com.hanson.service.impl;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.DmallException;
import com.hanson.mapper.AuthGroupMapper;
import com.hanson.mapper.AuthorityMapper;
import com.hanson.pojo.vo.AuthGroup;
import com.hanson.pojo.vo.Authority;
import com.hanson.service.AuthGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: manager
 * @description:
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 14:11
 **/
@Service
public class AuthGroupServiceImpl implements AuthGroupService {
    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public CommonResult<AuthGroup> authGroupInfo(Integer groupId) {
        AuthGroup authGroup = authGroupMapper.doGetGroupById(groupId);
        if (authGroup == null) throw new NullPointerException("没有该权限组信息");
        return new CommonResult<>(ResultCode.SUCCESS,authGroup);
    }

    @Override
    public PageResult<List<AuthGroup>> authGroupSplit(Integer page, Integer limit, String key) {
        String newKey;
        int total;
        if (key == null || "".equals(key.replaceAll(" ",""))){
            newKey = null;
            total = authGroupMapper.doCounts();
        }else {
            newKey = key.replaceAll(" ","");
            total = authGroupMapper.doSearchGroup(newKey).size();
        }
        if (total <= 0) throw new NullPointerException("暂无匹配的权限组数据");
        int start = (page - 1) * limit;
        List<AuthGroup> authGroups = authGroupMapper.doGroupSplit(start, limit, newKey);
        if (authGroups == null || authGroups.size() <= 0) throw new NullPointerException("暂无匹配的权限组数据");
        return new PageResult<>(ResultCode.SUCCESS,total,authGroups);
    }

    @Override
    public CommonResult<Integer> authGroupCreate(String groupName) {
        Integer cnum = authGroupMapper.doCreateAuthGroup(groupName);
        return new CommonResult<>(ResultCode.SUCCESS,cnum);
    }

    @Override
    public CommonResult<Integer> authGroupUpdate(Integer groupId, String groupName) {
        //检查是否为默认权限组
        AuthGroup authGroup = authGroupMapper.doGetGroupByName(Constant.DEFAULT_AUTH_GROUP);
        if (authGroup != null && groupName.equals(authGroup.getGroupName())) throw new DmallException("不能修改为默认权限组！");
        Integer unum = authGroupMapper.doUpdateAuthGroup(groupId, groupName);
        return new CommonResult<>(ResultCode.SUCCESS,unum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> authGroupDeleteById(Integer groupId, boolean isChecked) {
        //检查是否是默认权限组，不允许删除
        AuthGroup authGroup = authGroupMapper.doGetGroupById(groupId);
        if (authGroup != null && Constant.DEFAULT_AUTH_GROUP.equals(authGroup.getGroupName()))throw new DmallException("默认权限组不允许删除！");
        if (!isChecked && authGroup != null){
            //检查权限组是否正在被使用
            List<Authority> auths = authorityMapper.doGetAuthorityByGroup(authGroup.getGroupId());
            if (auths != null && auths.size() > 0){
                //获取正在使用该权限组的权限名信息
                String msg;
                if (auths.size() > 5){
                    //数据过多，集中反馈
                    msg = "统计发现：权限组正在被" + auths.size() + "个权限使用中，是否要将权限恢复为默认权限组，继续删除该权限组？";
                }else {
                    StringBuilder stb = new StringBuilder("统计发现：权限组正在被权限【");
                    for (Authority auth : auths) {
                        stb.append(auth.getAuthorityDetails());
                        stb.append("，");
                    }
                    stb.deleteCharAt(stb.length() -1);
                    stb.append("】使用中，是否要将权限恢复为默认权限组，继续删除该权限组？");
                    msg = stb.toString();
                }
                //返回询问是否继续删除该权限组
                return new CommonResult<>(ResultCode.FAILED,auths.size(),msg);
            }
        }
        //经过询问继续执行
        //恢复权限默认权限组
        if (authGroup != null){
            //获取默认权限组的id
            AuthGroup group = authGroupMapper.doGetGroupByName(Constant.DEFAULT_AUTH_GROUP);
            List<Authority> auths = authorityMapper.doGetAuthorityByGroup(authGroup.getGroupId());
            //统计更新
            int count = 0;
            for (Authority auth : auths) {
                auth.setGroupId(group.getGroupId());
            }
            for (Authority auth : auths) {
                Integer unum = authorityMapper.doUpdateAuthority(auth);
                if (unum > 0) count++;
            }
            if (count < auths.size()) throw new DmallException("恢复默认权限组出错");
        }
        //删除该权限组
        Integer num = authGroupMapper.doDeleteAuthGroup(groupId);
        if (num > 0) return new CommonResult<>(ResultCode.SUCCESS,num,"权限组已删除");
        return new CommonResult<>(ResultCode.FAILED,num);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> authGroupDeleteByName(String groupName, boolean isChecked) {
        AuthGroup authGroup = authGroupMapper.doGetGroupByName(groupName);
        if (authGroup == null) throw new NullPointerException("没有名称：" + groupName +"的权限组信息");
        return authGroupDeleteById(authGroup.getGroupId(), isChecked);
    }
}
