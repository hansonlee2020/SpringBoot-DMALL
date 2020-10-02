package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 角色
 * @param:
 * @author: Hanson
 * @create: 2020-09-15 17:01
 **/
@ApiModel(value = "角色对象",description = "角色表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    //角色ID
    @ApiModelProperty(name = "roleId",value = "角色ID",dataType = "Integer")
    private Integer roleId;
    //角色状态
    @ApiModelProperty(name = "state",value = "角色状态",dataType = "Integer")
    private Integer state;
    //角色名称
    @ApiModelProperty(name = "roleName",value = "角色名称",dataType = "String")
    private String roleName;

}
