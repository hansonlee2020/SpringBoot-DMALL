package com.hanson.service.impl;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.DmallException;
import com.hanson.mapper.*;
import com.hanson.pojo.dto.GroupAuth;
import com.hanson.pojo.dto.RoleAuth;
import com.hanson.pojo.dto.RoleEditTable;
import com.hanson.pojo.dto.UserAuthority;
import com.hanson.pojo.vo.*;
import com.hanson.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: 角色接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 13:55
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private RolePermsMapper rolePermsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public CommonResult<List<Role>> getRoles() {
        List<Role> roles = roleMapper.doGetRoles();
        if (roles == null) throw new NullPointerException("没有任何角色数据");
        if (roles.size() <= 0) throw new NullPointerException("没有任何角色数据");
        else return new CommonResult<>(ResultCode.SUCCESS,roles);
    }

    @Override
    public CommonResult<Role> getRoleById(Integer rid) {
        Role role = roleMapper.doGetRoleById(rid);
        if (role == null) throw new NullPointerException("没有该角色");
        return new CommonResult<>(ResultCode.SUCCESS,role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> createRole(Role role, Set<Integer> aids) {
        //查询角色是否存在
        Role hasRole = roleMapper.doGetRoleByName(role.getRoleName());
        if (hasRole != null) throw new DmallException("角色已存在，不能重复创建！");
        //新建角色
        Integer num = roleMapper.doCreateRole(role);
        //获取角色信息
        Role crole = roleMapper.doGetRoleByName(role.getRoleName());
        if (crole == null) throw new NullPointerException("无法创建角色");
        //新建角色-权限
        if (aids != null && aids.size() > 0){
            Integer cnum = rolePermsMapper.doCreateRolePerms(crole.getRoleId(),aids);
            if (cnum <= 0 ) throw new NullPointerException("新建角色授权失败！");
        }
        if (num > 0) return new CommonResult<>(ResultCode.SUCCESS,num);
        return new CommonResult<>(ResultCode.FAILED,num);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> updateRole(RoleEditTable<Integer> roleEditTable) {
        //更新角色信息
        Role role = roleMapper.doGetRoleByName(roleEditTable.getRname());
        if (role == null) throw new NullPointerException("没有该角色信息");
        roleMapper.doUpdateRole(new Role(role.getRoleId(), roleEditTable.getState(), roleEditTable.getRname()));
        //检查是否为超级管理员，如果是，授予全部权限，特殊模式，系统默认角色id为 1 的为超级管理员
        if (Integer.valueOf(Constant.SYS).equals(role.getRoleId())){
            List<Authority> authorities = authorityMapper.doGetAuthority();
            if (authorities == null || authorities.size() <= 0) throw new NullPointerException("权限表数据为空!");
            //获取超级管理员本身已经拥有的授权,非系统初始化
            List<RolePerms> rolePerms = rolePermsMapper.doGetPermsByRid(role.getRoleId());
            //比较权限id和已经授予的权限id
            Set<Integer> authsId = new HashSet<>();
            for (Authority authority : authorities) {
                authsId.add(authority.getAuthorityId());
            }
            if (rolePerms != null && rolePerms.size() >0){
                for (RolePerms rolePerm : rolePerms) {
                    authsId.remove(rolePerm.getAuthorityId());//从集合中移除已经授权的id
                }
                //如果集合不为空，说明有新的权限未授予
                if (authsId.size() > 0){
                    //授权，保证超级管理员拥有所有的权限
                    rolePermsMapper.doCreateRolePerms(role.getRoleId(), authsId);
                }
                return new CommonResult<>(ResultCode.SUCCESS,null);
            }
            //说明系统被初始化，没有超级管理员的角色授权数据
            Integer cnum = rolePermsMapper.doCreateRolePerms(roleEditTable.getRid(), authsId);
            return new CommonResult<>(ResultCode.SUCCESS,cnum);
        }
        //非超级管理员，正常授权模式
        //删除角色原来的---<角色-权限>---映射信息
        rolePermsMapper.doDeleteRolePerms(role.getRoleId());
        //重新添加---<角色-权限>---映射信息
        if (roleEditTable.getAuths() != null && roleEditTable.getAuths().size() > 0){
            rolePermsMapper.doCreateRolePerms(role.getRoleId(), new HashSet<>(roleEditTable.getAuths()));
        }
        return new CommonResult<>(ResultCode.SUCCESS,null);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> deleteRole(Integer rid, boolean isChecked) {
        //如果有管理员用户使用了该角色，则询问是否恢复管理员用户的角色为默认
        List<UserRole> userRoles = userRoleMapper.doGetUserRolesByRid(rid);
        //返回，等待操作用户确认
        if (!isChecked && userRoles != null && userRoles.size() > 0){
            StringBuilder msg = null;
            if (userRoles.size() < 3){
                msg = new StringBuilder("管理员【");
                for (UserRole userRole : userRoles) {
                    User user = userMapper.doGetUserById(userRole.getUserId());
                    if (user != null){
                        msg.append(user.getUserName()).append("，");
                    }
                }
                if (msg.toString().endsWith("，")) msg = new StringBuilder(msg.substring(0, msg.length() - 1));
                msg.append("】拥有这些角色");
            }else if (userRoles.size() > 3 ) msg = new StringBuilder("统计有" + userRoles.size() + "位管理员正在使用该角色");
            else msg = new StringBuilder();
            msg.append(",是否将这些管理员的角色设置为默认角色，然后继续删除该角色？");
            CommonResult<Integer> res = new CommonResult<>(ResultCode.FAILED, userRoles.size(), msg.toString());
            res.setType("sure");
            return res;
        }
        if (userRoles != null && userRoles.size() > 0){
            //接收确认，执行恢复默认
            for (UserRole userRole : userRoles) {
                //检查用户是否拥有了默认角色，如果没有，添加
                List<UserRole> hasRoles = userRoleMapper.doGetUserRolesByUid(userRole.getUserId());
                HashSet<Integer> rolesId = new HashSet<>();
                for (UserRole hasRole : hasRoles) {
                    rolesId.add(hasRole.getRoleId());
                }
                if (!rolesId.contains(Constant.GUEST)){
                    //没有默认角色
                    Integer cnum = userRoleMapper.doCreateUserRole(new UserRole(userRole.getUserId(), Constant.GUEST));
                    if (cnum <= 0 ) throw new DmallException("为ID：" + userRole.getUserId() +"管理员恢复默认角色出错");
                }
                //已经拥有了默认角色，开始执行抹除拥有将要删除角色的管理员的--<用户，角色>信息
                Integer dnum = userRoleMapper.doDeleteUserRoleWithRid(userRole.getUserId(), rid);
                if (dnum <= 0 ) throw new DmallException("删除角色失败");
            }
        }
        //删除角色
        roleMapper.doDeleteRole(rid);
        //删除---<角色-权限>----映射关系
        rolePermsMapper.doDeleteRolePerms(rid);
        return new CommonResult<>(ResultCode.SUCCESS,null);
    }

    @Override
    public PageResult<List<Role>> rolesSplit(Integer currentPage, Integer limit, String key) {
        String newKey;
        int total;
        if (key == null | "".equals(key)) {
            newKey = null;
            total = roleMapper.doCounts();
        }
        else {
            newKey = key.replaceAll(" ","");
            List<Role> roles = roleMapper.doRoleSearch(newKey);
            if (roles == null || roles.size() <= 0) throw new NullPointerException("暂无匹配的数据");
            total = roles.size();
        }
        int start = (currentPage - 1) * limit;
        List<Role> rolesList = roleMapper.doRoleSplit(start, limit, newKey);
        if (rolesList == null || rolesList.size() <=0) throw new NullPointerException("暂无匹配的数据");
        return new PageResult<>(ResultCode.SUCCESS,total,rolesList);
    }


    @Override
    public CommonResult<List<GroupAuth<UserAuthority>>> getPerms() {
        List<Authority> auths = authorityMapper.doGetAuthority();
        if (auths == null) throw new NullPointerException("没有任何权限信息");
        List<AuthGroup> groups = authGroupMapper.doGetGroups();
        if (groups == null) throw new NullPointerException("没有任何权限组信息");
        List<GroupAuth<UserAuthority>> groupsAuth = new ArrayList<>();
        for (AuthGroup group : groups) {
            //如果要复用authList，需要对groupAuth进行流序列化反序列化来深度克隆对象再封装到groupsAuth
            List<UserAuthority> authList = new ArrayList<>();
            for (Authority auth : auths) {
                if (auth.getGroupId() == null) throw new NullPointerException("权限的权限组id为空");
                if (auth.getGroupId().equals(group.getGroupId())) {
                    UserAuthority uat = new UserAuthority(null,auth,false);
                    authList.add(uat);
                }
            }
            GroupAuth<UserAuthority> groupAuth = new GroupAuth<>(group,authList);
            groupsAuth.add(groupAuth);
        }
        return new CommonResult<>(ResultCode.SUCCESS,groupsAuth);
    }

    @Override
    public CommonResult<List<GroupAuth<RoleAuth>>> getRolePerms(Integer roleId) {
        Role role = roleMapper.doGetRoleById(roleId);
        if (role == null) throw new NullPointerException("没有找到该角色");
        List<RolePerms> rolePerms = rolePermsMapper.doGetPermsByRid(role.getRoleId());
        if (rolePerms == null) throw new NullPointerException("角色没有任何权限");
        //保存<权限组-角色所有权限>
        List<GroupAuth<RoleAuth>> groupAuths = new ArrayList<>();
        //获取权限组
        List<AuthGroup> authGroups = authGroupMapper.doGetGroups();
        if (authGroups == null) throw new NullPointerException("权限组表没有任何数据");
        //保存权限id
        Set<Integer> authsId = new HashSet<>();
        for (RolePerms rolePerm : rolePerms) {
            authsId.add(rolePerm.getAuthorityId());
        }
        for (AuthGroup authGroup : authGroups) {
            //保存角色-权限
            List<RoleAuth> roleAuths = new ArrayList<>();
            Set<Authority> auths = null;
            if (authsId.size() > 0) auths = authorityMapper.doGetAuthoritiesByIds(authsId);
            if (auths == null || auths.size() <= 0) throw new NullPointerException("权限表没有该角色权限数据");
            for (Authority auth : auths) {
                if (authGroup.getGroupId().equals(auth.getGroupId())){
                    RoleAuth roleAuth = new RoleAuth(role.getRoleName(),auth.getAuthorityId(),auth.getAuthorityDetails(),true);
                    roleAuths.add(roleAuth);
                }
            }
            GroupAuth<RoleAuth> groupAuth = new GroupAuth<>(authGroup.getGroupId(),authGroup.getGroupName(),roleAuths);
            groupAuths.add(groupAuth);
        }
        return new CommonResult<>(ResultCode.SUCCESS,groupAuths);
    }

    @Override
    public CommonResult<RoleEditTable<GroupAuth<RoleAuth>>> getRoleInfo(Integer roleId) {
        Role role = roleMapper.doGetRoleById(roleId);
        if (role == null) throw new NullPointerException("没有该用户");
        RoleEditTable<GroupAuth<RoleAuth>> roleEditTable = new RoleEditTable<>(roleId,role.getRoleName(),role.getState());
        //获取拥有的权限信息
        List<RolePerms> rolePerms = rolePermsMapper.doGetPermsByRid(role.getRoleId());
        //获取权限组
        List<AuthGroup> authGroups = authGroupMapper.doGetGroups();
        //保存<权限组-角色所有权限>
        List<GroupAuth<RoleAuth>> groupAuths = new ArrayList<>();
        if (authGroups == null) throw new NullPointerException("权限组表没有任何数据");
        //保存角色原来拥有的权限id
        Set<Integer> authsId = new HashSet<>();
        if (rolePerms != null){
            for (RolePerms rolePerm : rolePerms) {
                authsId.add(rolePerm.getAuthorityId());
            }
        }
        boolean flag = false;//是否拥有权限
        for (AuthGroup authGroup : authGroups) {
            //保存角色-权限
            List<RoleAuth> roleAuths = new ArrayList<>();
            //查询权限表全部权限数据
            List<Authority> auths = authorityMapper.doGetAuthority();
            if (auths == null || auths.size() <= 0) throw new NullPointerException("权限表没有权限数据");
            for (Authority auth : auths) {
                if (authGroup.getGroupId().equals(auth.getGroupId())){
                    if (authsId.size() > 0 && authsId.contains(auth.getAuthorityId())){//比对权限id，拥有权限则标志位改为true
                        flag = true;
                    }
                    RoleAuth roleAuth = new RoleAuth(role.getRoleName(),auth.getAuthorityId(),auth.getAuthorityDetails(),flag);
                    roleAuths.add(roleAuth);
                    flag = false;//重置标志位
                }
            }
            GroupAuth<RoleAuth> groupAuth = new GroupAuth<>(authGroup.getGroupId(),authGroup.getGroupName(),roleAuths);
            groupAuths.add(groupAuth);
        }
        roleEditTable.setAuths(groupAuths);
        return new CommonResult<>(ResultCode.SUCCESS,roleEditTable);
    }
}
