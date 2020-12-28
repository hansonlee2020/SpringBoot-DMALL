package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 用户角色
 * @param:
 * @author: Hanson
 * @create: 2020-09-15 19:01
 **/
@ApiModel(value = "管理员-角色对象",description = "管理员-角色表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    //主键ID
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //管理员ID
    @ApiModelProperty(name = "userId",value = "管理员ID",dataType = "String")
    private String userId;
    //角色ID
    @ApiModelProperty(name = "roleId",value = "角色ID",dataType = "Integer")
    private Integer roleId;

    public UserRole(String userId,Integer roleId){
        this(null,userId,roleId);
    }
}
