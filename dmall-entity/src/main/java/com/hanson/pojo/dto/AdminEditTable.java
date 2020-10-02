package com.hanson.pojo.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @program: manager
 * @description: 编辑管理员信息数据表
 * @param:
 * @author: Hanson
 * @create: 2020-09-18 15:59
 **/
@ApiModel(value = "管理员表单对象",description = "接收前端的管理员信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminEditTable implements Serializable {
    @ApiModelProperty(name = "uid",value = "管理员ID",dataType = "String")
    private String uid;
    @ApiModelProperty(name = "uname",value = "管理员名称",dataType = "String")
    private String uname;
    @ApiModelProperty(name = "state",value = "管理员状态",dataType = "Integer")
    private Integer state;
    @ApiModelProperty(name = "pass",value = "管理员密码",dataType = "String")
    private String pass;
    @ApiModelProperty(name = "rids",value = "管理员角色ID组",dataType = "Set<Integer>")
    private Set<Integer> rids;
}
