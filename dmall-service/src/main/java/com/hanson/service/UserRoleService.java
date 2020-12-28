package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.pojo.dto.GroupAuth;
import com.hanson.pojo.dto.UserAuthority;
import com.hanson.pojo.vo.Authority;
import com.hanson.pojo.vo.UserRole;

import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: 用户角色接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 16:43
 **/
public interface UserRoleService {

    public CommonResult<List<UserRole>> getUserRoles(String id);
    public CommonResult<Integer> createUserRole(UserRole userRole);
    public CommonResult<Integer> updateUserRole(UserRole userRole);
    public CommonResult<Integer> deleteUserRole(UserRole userRole);
    public CommonResult<Integer> createUserRoles(String uname, Set<Integer> rids);
    public CommonResult<Integer> updateUserRoles(String uname, Set<Integer> rids);
    public CommonResult<Integer> deleteUserRoles(String uname, Set<Integer> rids);
    public CommonResult<List<Authority>> getUserPerms(String uname);
    public CommonResult<List<GroupAuth<UserAuthority>>> getAuths(String uname);
}
