package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 角色-权限
 * @param:
 * @author: Hanson
 * @create: 2020-09-15 17:02
 **/
@ApiModel(value = "角色权限对象",description = "角色权限表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePerms implements Serializable {
    //主键ID
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //角色ID
    @ApiModelProperty(name = "roleId",value = "角色ID",dataType = "Integer")
    private Integer roleId;
    //权限ID
    @ApiModelProperty(name = "authorityId",value = "权限ID",dataType = "Integer")
    private Integer authorityId;

    public RolePerms(Integer roleId,Integer authorityId){
        this(null,roleId,authorityId);
    }
}
