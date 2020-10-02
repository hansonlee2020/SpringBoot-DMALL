package com.hanson.pojo.dto;

import com.hanson.pojo.vo.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 用户-权限信息,包装用户-权限之间的关系
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 20:11
 **/
@ApiModel(value = "管理员-权限对象",description = "封装管理员及权限信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthority implements Serializable {
    //用户名
    @ApiModelProperty(name = "uname",value = "用户名",dataType = "String")
    private String uname;
    //权限id
    @ApiModelProperty(name = "authId",value = "权限ID",dataType = "Integer")
    private Integer authId;
    //权限描述
    @ApiModelProperty(name = "auth",value = "权限描述",dataType = "String")
    private String auth;
    //是否拥有该权限
    @ApiModelProperty(name = "hasAuth",value = "是否拥有该权限",dataType = "boolean")
    private boolean hasAuth;
    public UserAuthority(String name, Authority authority,boolean hasAuth){
        this.uname = name;
        this.authId = authority.getAuthorityId();
        this.auth = authority.getAuthorityDetails();
        this.hasAuth = hasAuth;
    }
}
