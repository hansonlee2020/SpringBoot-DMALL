package com.hanson.service.impl;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.mapper.*;
import com.hanson.pojo.dto.GroupAuth;
import com.hanson.pojo.dto.UserAuthority;
import com.hanson.pojo.vo.*;
import com.hanson.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description:
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 16:48
 **/
@Service
public class UserRolesImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private RolePermsMapper rolePermsMapper;

    @Override
    public CommonResult<List<UserRole>> getUserRoles(String id) {
        return null;
    }

    @Override
    public CommonResult<Integer> createUserRole(UserRole userRole) {
        return null;
    }

    @Override
    public CommonResult<Integer> updateUserRole(UserRole userRole) {
        return null;
    }

    @Override
    public CommonResult<Integer> deleteUserRole(UserRole userRole) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> createUserRoles(String uname, Set<Integer> rids) {
        if (uname == null | "".equals(uname)) throw new NullPointerException("用户名不能为空");
        //默认system为超级账户，不允许撤销其超级管理员角色
        if ("system".equals(uname)) rids.add(Constant.SYS);
        User user = userMapper.doGetUserByName(uname);
        if (user == null) throw new NullPointerException("用户为空");
        Integer dnum = userRoleMapper.doDeleteUserRole(user.getUserId());
        if (dnum <= 0) throw new NullPointerException("该用户没有任何角色,系统要求用户至少有一个角色！");
        Integer cnum = userRoleMapper.doCreateUserRoles(user.getUserId(), rids);
        if (cnum <= 0) throw new NullPointerException("用户添加角色为空");
        return new CommonResult<>(ResultCode.SUCCESS,cnum);
    }

    @Override
    public CommonResult<Integer> updateUserRoles(String uname, Set<Integer> rids) {
        return null;
    }

    @Override
    public CommonResult<Integer> deleteUserRoles(String uname, Set<Integer> rids) {
        return null;
    }

    @Override
    public CommonResult<List<Authority>> getUserPerms(String uname) {
        return null;
    }

    @Override
    public CommonResult<List<GroupAuth<UserAuthority>>> getAuths(String uname) {
        List<Authority> auths = authorityMapper.doGetAuthority();
        if (auths == null) throw new NullPointerException("没有任何权限信息");
        List<AuthGroup> groups = authGroupMapper.doGetGroups();
        if (groups == null) throw new NullPointerException("没有任何权限组信息");
        //查询用户所拥有的权限
        User user = userMapper.doGetUserByName(uname);
        List<UserRole> userRoles = userRoleMapper.doGetUserRolesByUid(user.getUserId());
        List<Integer> hasPerms = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            List<RolePerms> rolePerms = rolePermsMapper.doGetPermsByRid(userRole.getRoleId());
            for (RolePerms rolePerm : rolePerms) {
                hasPerms.add(rolePerm.getAuthorityId());
            }
        }
        //封装<权限组-<用户-权限>>
        List<GroupAuth<UserAuthority>> groupsAuth = new ArrayList<>();
        for (AuthGroup group : groups) {
            //如果要复用authList，需要对groupAuth进行流序列化反序列化来深度克隆对象再封装到groupsAuth
            List<UserAuthority> authList = new ArrayList<>();
            for (Authority auth : auths) {
                if (auth.getGroupId() == null) throw new NullPointerException("权限的权限组id为空");
                if (auth.getGroupId().equals(group.getGroupId())) {
                    boolean flag = false;//是否拥有权限
                    if (hasPerms.contains(auth.getAuthorityId())) flag = true;
                    UserAuthority uat = new UserAuthority(user.getUserName(),auth,flag);
                    authList.add(uat);
                }
            }
            GroupAuth<UserAuthority> groupAuth = new GroupAuth<>(group,authList);
            groupsAuth.add(groupAuth);
        }
        return new CommonResult<>(ResultCode.SUCCESS,groupsAuth);
    }
}
