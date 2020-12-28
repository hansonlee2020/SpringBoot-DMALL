package com.hanson.config;

import com.hanson.common.constant.Constant;
import com.hanson.mapper.*;
import com.hanson.pojo.vo.*;
import com.hanson.utils.DMStringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: dmall的userRealm
 * @param:
 * @author: Hanson
 * @create: 2020-09-04 18:37
 **/
public class DMUserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermsMapper rolePermsMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        System.out.println(principals);//认证时候，传进来的principal是什么principals就是什么
            User login = (User) principals.getPrimaryPrincipal();
        User user = userMapper.doGetUserByName(login.getUserName());
        //权限
        Set<String> permissions = new HashSet<>();
        //角色
        Set<String> roles = new HashSet<>();
        List<UserRole> userRoles = userRoleMapper.doGetUserRolesByUid(user.getUserId());
        if (userRoles != null){
            for (UserRole userRole : userRoles) {
                List<RolePerms> rolePerms = rolePermsMapper.doGetPermsByRid(userRole.getRoleId());
                if (rolePerms != null && rolePerms.size() > 0) {
                    Set<Integer> ids = new HashSet<>();
                    for (RolePerms rolePerm : rolePerms) {
                        //角色是否启用
                        Role role = roleMapper.doGetRoleById(rolePerm.getRoleId());
                        if (role.getState() == Constant.ENABLE_STATE){
                            ids.add(rolePerm.getAuthorityId());
                        }
                    }
                    Set<Authority> auths = authorityMapper.doGetAuthoritiesByIds(ids);
                    if (auths != null && auths.size() > 0) {
                        for (Authority auth : auths) {
                            String perms = DMStringUtils.subStringBySymbol(auth.getAuthorityName());
                            permissions.add(perms);
                        }
                    }
                }
                Role role = roleMapper.doGetRoleById(userRole.getRoleId());
                if (role != null && role.getState() == Constant.ENABLE_STATE) roles.add(role.getRoleName());
            }
            info.addStringPermissions(permissions);
            info.addRoles(roles);
            return info;
        }
        else return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utk = (UsernamePasswordToken)token;
        User user = userMapper.doGetLoginUserByName(utk.getUsername());
        if (user == null) return null;
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getEncryptionSalt());
        return new SimpleAuthenticationInfo(
                user,
                user.getUserPwd(),
                credentialsSalt,
                this.getName()
        );
    }
}
