package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 权限组
 * @param:
 * @author: Hanson
 * @create: 2020-09-17 18:47
 **/
@ApiModel(value = "权限组对象",description = "权限组表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthGroup implements Serializable {
    //权限组ID
    @ApiModelProperty(name = "groupId",value = "权限组ID",dataType = "Integer")
    private Integer groupId;
    //权限组名称
    @ApiModelProperty(name = "groupName",value = "权限组名称",dataType = "String")
    private String groupName;
}
