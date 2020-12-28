package com.hanson.pojo.dto;

import com.hanson.common.constant.Constant;
import com.hanson.pojo.vo.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 管理员用户类，用于返回管理员信息给数据展示页面
 * @param:
 * @author: Hanson
 * @create: 2020-04-23 15:29
 **/
@ApiModel(value = "管理员对象",description = "封装管理员信息，返回前端")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUser implements Serializable,Cloneable {
    //管理员用户id
    @ApiModelProperty(name = "uid",value = "管理员用户ID",dataType = "String")
    private String uid;
    //管理员用户名
    @ApiModelProperty(name = "uname",value = "管理员用户名",dataType = "String")
    private String uname;
    //管理员用户密码
    @ApiModelProperty(name = "password",value = "管理员用户密码",dataType = "String")
    private String password;
    //管理员用户状态
    @ApiModelProperty(name = "state",value = "管理员用户状态",dataType = "String")
    private String state;
    //管理员用户拥有的权限
    @ApiModelProperty(name = "perms",value = "管理员用户拥有的权限",dataType = "Set<Integer>")
    private Set<Integer> perms;
    //管理员角色
    @ApiModelProperty(name = "roles",value = "管理员角色",dataType = "List<String>")
    private List<String> roles;
    public SystemUser(
            String userId,
            String userName,
            String userPwd,
            Integer userState,
            Set<Integer> perms,
            List<String> roles){
        this.uid = userId;
        this.uname = userName;
        this.password = userPwd;
        this.perms = new HashSet<>(perms);//方案1
        this.roles = new ArrayList<>(roles);//方案1
//        this.perms = perms;//方案2
//        this.roles = roles;//方案2
        if (userState == Constant.DISABLE_STATE) this.state = Constant.DISABLE;
        else if (userState == Constant.ENABLE_STATE) this.state = Constant.ENABLE;
        else if (userState == Constant.FREEZE_STATE) this.state = Constant.FREEZE;
        else this.state = Constant.NON;
    }
    public SystemUser(User user,Set<Integer> perms,List<String> roles){
        this(user.getUserId(),user.getUserName(),null,user.getUserState(),perms,roles);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SystemUser user = (SystemUser) super.clone();
        if (this.perms != null) {
            Set<Integer> perms = new HashSet<>(this.perms);
            user.setPerms(perms);
        }
        if (roles != null) {
            List<String> roles = new ArrayList<>(this.roles);
            user.setRoles(roles);
        }
        return user;
    }
}
