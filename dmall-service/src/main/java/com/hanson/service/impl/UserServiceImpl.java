package com.hanson.service.impl;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.DmallException;
import com.hanson.mapper.RoleMapper;
import com.hanson.mapper.RolePermsMapper;
import com.hanson.mapper.UserMapper;
import com.hanson.mapper.UserRoleMapper;
import com.hanson.pojo.dto.AdminEditTable;
import com.hanson.pojo.dto.SystemUser;
import com.hanson.pojo.vo.*;
import com.hanson.service.UserService;
import com.hanson.utils.DMStringUtils;
import com.hanson.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @program: manager
 * @description:
 * @param:
 * @author: Hanson
 * @create: 2020-09-04 19:05
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermsMapper permsMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User getLoginUser(User login) {
        return userMapper.doGetLoginUser(login);
    }

    @Override
    public User getLoginUserByName(String userName) {
        return userMapper.doGetLoginUserByName(userName);
    }

    @Override
    public CommonResult<User> getUser(String id) {
        User user = userMapper.doGetUserById(id);
        return new CommonResult<>(user);
    }

    @Override
    public PageResult<List<SystemUser>> getUserSplit(Integer pageSize, Integer currentPage, String key) {
        String newKey;//保存格式key
        long total;
        int pages;
        if (key == null) {
            newKey = null;
            total = (long)userMapper.doCounts();
        } else {
            newKey = key.replaceAll(" ","");
            total =(long) userMapper.doSearchUserByName(newKey).size();//总数据量
        }
        Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引

        //以下一段if-else在系统重构后没有意义，前端使用了layui模板，不再是自己写原生了，但我还是想留着......
        if (total % pageSize != 0){
            pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
        }else {
            pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
        }
        List<User> users = userMapper.doUserSplit(startIndex,pageSize,newKey);
        if (users == null || users.size() <= 0) throw new NullPointerException("暂无匹配的数据");
        List<SystemUser> systemUsers = new ArrayList<>();
        List<String> rolesName = new ArrayList<>();
        Set<Integer> authIds = new HashSet<>();
        for (User user : users) {
            rolesName.clear();
            authIds.clear();
//            List<String> rolesName = new ArrayList<>();//方案3
//            Set<Integer> authIds = new HashSet<>();//方案3
            String userId = user.getUserId();
            List<UserRole> userRoles = userRoleMapper.doGetUserRolesByUid(userId);
            for (UserRole userRole : userRoles) {
                Integer roleId = userRole.getRoleId();
                Role role = roleMapper.doGetRoleById(roleId);
                if (role != null) {
                    rolesName.add(role.getRoleName());
                    List<RolePerms> rolePerms = permsMapper.doGetPermsByRid(role.getRoleId());
                    if (rolePerms == null) throw new NullPointerException("用户" + user.getUserName() + "找不到对应的role-Perms");
                    else {
                        for (RolePerms rolePerm : rolePerms) {
                            Integer authorityId = rolePerm.getAuthorityId();
                            authIds.add(authorityId);
                        }
                    }
                }
                else throw new NullPointerException("用户" + user.getUserName() + "拥有的角色id=" + roleId + "无法在角色列表中找到！");
            }
            /*q
            ----------这里需要稍微注意，由于复用了list集合和set集合（通过clear方法清空集合数据，复用集合）
            ----------来避免重复创建集合，消耗较多系统资源的问题，这两集合的引用是直接
            ----------丢进去SystemUser实例里面的，会有一个引用问题，当集合发生了改变，
            ----------SystemUser实例的引用类型属性也会发生改变，在这里我提供了三种解决方案：
            ----------
            ----------解决方案1：在SystemUser构造函数初始化时，使用new ArrayList<>();new HashSet<>();
            ----------来进行赋值，由于本身list集合里放的是String类型，Set集合里放的是Integer类型，
            ----------而String是不可变对象，而Integer是每次都重新new的Integer对象，所以不影响
            ----------但是如果List集合放的是可变对象类型（？），那么就需要进行深度克隆来赋值给集合
            ---------元素了，这时候你的类型（？）需要实现深度克隆，
            ---------方法一：实现Cloneable接口覆写clone（）方法
            ---------方法二：实现序列化接口Serializable，提供方法来使用序列化和反序列化进行对象的深度克隆
            ---------
            ---------解决方案2：深度克隆SystemUser实例，然后将克隆实例丢进去List<SystemUser> systemUsers = new ArrayList<>();
            ---------
            ---------解决方案3：将List<String> rolesName = new ArrayList<>();Set<Integer> authIds = new HashSet<>();
            ---------放进循环里面去初始化，每次循环都进行初始化新的集合实例，这样，丢进去给SystemUser实例都是不一样的
            ---------
            ---------总的来说就是类中有属性是引用类型，导致了该引用所在内存数据的改变将影响所有引用该内存的实例
             */
            SystemUser systemUser = new SystemUser(user, authIds, rolesName);

            /*q
            -----------------------------------------方案1-----------------------------
             */
            systemUsers.add(systemUser);

            /*q
            -----------------------------------------方案2-----------------------------
             */
/*            try {
                SystemUser clone = (SystemUser) systemUser.clone();
                systemUsers.add(clone);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }*/
        }
        return new PageResult<>(ResultCode.SUCCESS,total,systemUsers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> createUser(AdminEditTable aet) {
        //验证数据是否存在
        User user = userMapper.doGetUserByName(aet.getUname());
        if (user != null) throw new DmallException("管理员名已存在！");
        //添加管理员
        Map<String, String> md5 = EncryptUtil.getMd5(aet.getPass(), aet.getPass() + aet.getUname());
        String password = md5.get(Constant.MAP_PASS);
        String salt = md5.get(Constant.MAP_SALT);
        String uuid = DMStringUtils.formatUUID(UUID.randomUUID());
        Integer num = userMapper.doCreateUser(new User(uuid, aet.getUname(), password, aet.getState(), salt));
        //为管理员新增角色
        Integer cnum = userRoleMapper.doCreateUserRoles(uuid, aet.getRids());
        if (cnum <= 0) throw new NullPointerException("管理员角色添加失败");
        if (num <= 0) return new CommonResult<>(ResultCode.FAILED,num);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> updateUser(AdminEditTable aet) {
        Map<String, String> md5 = EncryptUtil.getMd5(aet.getPass(), aet.getPass() + aet.getUname());
        String password = md5.get(Constant.MAP_PASS);
        String salt = md5.get(Constant.MAP_SALT);
        Integer num = userMapper.doUpdateUser(new User(aet.getUid(), aet.getUname(), password, aet.getState(), salt));
        Integer dnum = userRoleMapper.doDeleteUserRole(aet.getUid());
        if (dnum <= 0) throw new NullPointerException("管理员角色初始化失败");
        Integer cnum = userRoleMapper.doCreateUserRoles(aet.getUid(), aet.getRids());
        if (cnum <= 0) throw new NullPointerException("管理员角色添加失败");
        if (num <= 0) return new CommonResult<>(ResultCode.FAILED,num);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    public CommonResult<Integer> updateUserState(String uid, Integer state) {
        Integer num = userMapper.doUpdateUserState(uid, state);
        if (num <= 0) new CommonResult<>(ResultCode.FAILED,num, Constant.UPDATE_STATE_FAILED);
        return new CommonResult<>(ResultCode.SUCCESS,num,Constant.UPDATE_STATE_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Integer> deleteUser(String id) {
        //删除管理员
        Integer num = userMapper.doDeleteUser(id);
        //删除管理员-角色
        Integer dnum = userRoleMapper.doDeleteUserRole(id);
        if (num <= 0) new CommonResult<>(ResultCode.FAILED,num, Constant.DELETE_ADMIN_FAILED);
        return new CommonResult<>(ResultCode.SUCCESS,num,Constant.DELETE_ADMIN_SUCCESS);
    }

    @Override
    public CommonResult<List<Authority>> getAuthority() {
        return null;
    }

    @Override
    public CommonResult<Integer> batchAuthorizePermissions(String ids, String userId) {
        return null;
    }

    @Override
    public CommonResult<Integer> batchCancelPermission(String ids, String userId) {
        return null;
    }

    @Override
    public CommonResult<List<UserRole>> getUserRoles(String id) {
        List<UserRole> userRoles = userRoleMapper.doGetUserRolesByUid(id);
        if (userRoles == null) throw new NullPointerException("用户未拥有任何角色");
        else if (userRoles.size() <= 0) throw new  NullPointerException("用户未拥有任何角色");
        else return new CommonResult<>(ResultCode.SUCCESS,userRoles);
    }

    @Override
    public CommonResult<User> getUserByName(String name) {
        User user = userMapper.doGetUserByName(name);
        if (user == null) throw new NullPointerException("没有该管理员");
        return new CommonResult<>(ResultCode.SUCCESS,user);
    }
}
