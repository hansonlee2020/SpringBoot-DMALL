package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: manager
 * @description: 角色编辑
 * @param:
 * @author: Hanson
 * @create: 2020-09-19 17:45
 **/
@ApiModel(value = "角色对象",description = "封装角色信息，接收前端编辑的角色数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEditTable<T> implements Serializable {
    //角色ID
    @ApiModelProperty(name = "rid",value = "角色ID",dataType = "Integer")
    private Integer rid;
    //角色名称
    @ApiModelProperty(name = "rname",value = "角色名称",dataType = "String")
    private String rname;
    //角色状态
    @ApiModelProperty(name = "state",value = "角色状态",dataType = "Integer")
    private Integer state;
    //授予角色的权限，泛型，可以包装为任何类，只要包含权限信息即可
    @ApiModelProperty(name = "auths",value = "授予角色的权限",dataType = "List")
    private List<T> auths;
    public RoleEditTable(Integer rid,String rname,Integer state){
        this.rid = rid;
        this.rname = rname;
        this.state = state;
        this.auths = null;
    }
}
