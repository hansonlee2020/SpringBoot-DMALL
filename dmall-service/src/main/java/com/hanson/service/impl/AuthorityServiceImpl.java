package com.hanson.service.impl;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.exception.DmallException;
import com.hanson.mapper.AuthGroupMapper;
import com.hanson.mapper.AuthorityMapper;
import com.hanson.mapper.RoleMapper;
import com.hanson.mapper.RolePermsMapper;
import com.hanson.pojo.dto.AuthsInfo;
import com.hanson.pojo.vo.AuthGroup;
import com.hanson.pojo.vo.Authority;
import com.hanson.pojo.vo.Role;
import com.hanson.pojo.vo.RolePerms;
import com.hanson.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 权限接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:03
 **/
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private RolePermsMapper rolePermsMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public CommonResult<List<Authority>> getAuthorities() {
        return null;
    }

    @Override
    public CommonResult<AuthsInfo<AuthGroup>> getAuthorityInfo(Integer authId) {
        //获取权限信息
        Authority authority = authorityMapper.doGetAuthorityById(authId);
        if (authority == null) throw new NullPointerException("没有该权限信息");
        AuthGroup authGroup = authGroupMapper.doGetGroupById(authority.getGroupId());
        if (authGroup == null) throw new NullPointerException("没有该权限组信息");
        //获取所有的权限组信息
        List<AuthGroup> authGroups = authGroupMapper.doGetGroups();
        if (authGroups == null) throw new NullPointerException("权限表为空");
        AuthsInfo<AuthGroup> authsInfo = new AuthsInfo<>(authority, authGroup, authGroups);
        return new CommonResult<>(ResultCode.SUCCESS,authsInfo);
    }

    @Override
    public Set<Authority> getAuthorityByIds(Set<Integer> authorityIds) {
        return authorityMapper.doGetAuthoritiesByIds(authorityIds);
    }

    @Override
    public PageResult<List<AuthsInfo<AuthGroup>>> splitAuthority(Integer currentPage, Integer pageSize, String search) {
        String key ;
        long total;
        if (search == null) {
            key = null;
            total = authorityMapper.doCounts();
        }else {
            key = search.replaceAll(" ","");
            total = authorityMapper.doSearchAuthority(key).size();
        }
        int startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
        List<Authority> auths = authorityMapper.doSplitAuthority(startIndex, pageSize,key);//分页查询结果集
        if (auths == null || auths.size() <= 0) throw new NullPointerException("暂无匹配的权限数据");
        List<AuthsInfo<AuthGroup>> authsInfos = new ArrayList<>();
        for (Authority auth : auths) {
            Integer groupId = auth.getGroupId();
            AuthGroup authGroup = authGroupMapper.doGetGroupById(groupId);
            AuthsInfo<AuthGroup> authsInfo = new AuthsInfo<>(auth, authGroup,null);
            authsInfos.add(authsInfo);
        }
        return new PageResult<>(ResultCode.SUCCESS,total,authsInfos);
    }

    @Override
    public CommonResult<Integer> createAuthority(Authority authority) {
        //验证规则
        if (!isAuthorityName(authority)) throw new DmallException("权限名不符合规则");
        if (!isAuthorityField(authority)) throw new DmallException("资源字段不符合规则");
        Integer cnum = authorityMapper.doCreateAuthority(authority);
        return new CommonResult<>(ResultCode.SUCCESS,cnum);
    }

    @Override
    public CommonResult<Integer> updateAuthority(Authority authority) {
        //验证规则
        if (!isAuthorityName(authority)) throw new DmallException("权限名不符合规则");
        if (!isAuthorityField(authority)) throw new DmallException("资源字段不符合规则");
        Integer unum = authorityMapper.doUpdateAuthority(authority);
        return new CommonResult<>(ResultCode.SUCCESS,unum);
    }

    @Override
    public CommonResult<Integer> deleteAuthority(Integer aid, boolean flag) {
        //检查权限是否已经赋予给某个角色或管理员
        List<RolePerms> rolePerms = rolePermsMapper.doGetPermsByAuthId(aid);
        if (!flag && rolePerms != null && rolePerms.size() > 0){
            //获取已授权的角色
            String msg = "拥有该权限，是否将这些角色的权限回收，继续删除该权限？";
            if (rolePerms.size() > 5){//数据过多，集中处理
                String content = "统计有" + rolePerms.size() + "个角色" + msg;
                return new CommonResult<>(200,"sure",content,rolePerms.size());
            }
            StringBuilder stb = new StringBuilder("角色【");
            for (RolePerms rolePerm : rolePerms) {
                Role role = roleMapper.doGetRoleById(rolePerm.getRoleId());
                stb.append(role.getRoleName()+"，");
            }
            stb.deleteCharAt(stb.length()-1);
            stb.append("】");
            stb.append(msg);
            return new CommonResult<>(200,"sure",stb.toString(),rolePerms.size());
        }
        if (rolePerms != null){
            //精准删除----<角色，权限>信息
            for (RolePerms rolePerm : rolePerms) {
                rolePermsMapper.doDeleteRolePermsAccurate(rolePerm.getRoleId(),aid);
            }
        }
        //删除权限信息
        Integer dnum = authorityMapper.doDeleteAuthorityById(aid);
        CommonResult<Integer> res = new CommonResult<>(ResultCode.SUCCESS, dnum);
        if (dnum <= 0) res.setContent("已删除" + dnum +"个权限");
        return res;
    }

    @Override
    public CommonResult<List<AuthGroup>> getAuthGroups() {
        List<AuthGroup> authGroups = authGroupMapper.doGetGroups();
        if (authGroups == null) throw new NullPointerException("权限表为空");
        return new CommonResult<>(ResultCode.SUCCESS, authGroups);
    }

    /*
    * @description: 验证添加的权限名是否合法
    * @params: [authority] 需要验证的权限
    * @return: boolean 合法返回true，非法返回false
    * @Date: 2020/4/19
    */
    public boolean isAuthorityName(Authority authority){
        if ("authc".equals(authority.getAuthorityName()) | "anon".equals(authority.getAuthorityName())){
            return true;
        }else {
            if (authority.getAuthorityName().startsWith("perms[") && authority.getAuthorityName().endsWith("]")){
                String name = authority.getAuthorityName();
                String str = name.substring(name.indexOf('[', 4) + 1, name.lastIndexOf(']'));
                String regex = "\\w+(:\\w+)*?";
                return str.matches(regex);
            }
            return false;
        }
    }
    /*
    * @description: 验证添加的权限资源字段名是否合法
    * @params: [authority] 需要验证的权限
    * @return: boolean 合法返回true，非法返回false
    * @Date: 2020/4/19
    */
    public boolean isAuthorityField(Authority authority){
        String regex = "(/\\w+)+?(/\\*\\*)*";
        return authority.getAuthorityField().matches(regex);
    }
}
