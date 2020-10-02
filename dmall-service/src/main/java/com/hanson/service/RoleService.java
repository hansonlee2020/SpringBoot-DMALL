package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.pojo.dto.GroupAuth;
import com.hanson.pojo.dto.RoleAuth;
import com.hanson.pojo.dto.RoleEditTable;
import com.hanson.pojo.dto.UserAuthority;
import com.hanson.pojo.vo.Role;

import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: 角色service接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 13:52
 **/
public interface RoleService {

    public CommonResult<List<Role>> getRoles();
    public CommonResult<Role> getRoleById(Integer rid);
    public CommonResult<Integer> createRole(Role role, Set<Integer> aids);
    public CommonResult<Integer> updateRole(RoleEditTable<Integer> roleEditTable);
    public CommonResult<Integer> deleteRole(Integer rid, boolean isChecked);
    public PageResult<List<Role>> rolesSplit(Integer currentPage, Integer limit, String key);
    public CommonResult<List<GroupAuth<UserAuthority>>> getPerms();
    public CommonResult<List<GroupAuth<RoleAuth>>> getRolePerms(Integer roleId);
    public CommonResult<RoleEditTable<GroupAuth<RoleAuth>>> getRoleInfo(Integer roleId);
}
