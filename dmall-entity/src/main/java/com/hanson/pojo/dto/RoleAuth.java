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
 * @description: 角色-权限信息,包装角色-权限之间的关系
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 20:11
 **/
@ApiModel(value = "角色权限对象",description = "封装角色权限信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuth implements Serializable {
    //角色名
    @ApiModelProperty(name = "rname",value = "角色名",dataType = "String")
    private String rname;
    //权限id
    @ApiModelProperty(name = "authId",value = "权限ID",dataType = "Integer")
    private Integer authId;
    //权限描述
    @ApiModelProperty(name = "auth",value = "权限描述",dataType = "String")
    private String auth;
    //是否拥有该权限
    @ApiModelProperty(name = "hasAuth",value = "是否拥有该权限",dataType = "boolean")
    private boolean hasAuth;
    public RoleAuth(String rname, Authority authority, boolean hasAuth){
        this.rname = rname;
        this.authId = authority.getAuthorityId();
        this.auth = authority.getAuthorityDetails();
        this.hasAuth = hasAuth;
    }
}
