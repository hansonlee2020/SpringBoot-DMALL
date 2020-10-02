package com.hanson.pojo.dto;

import com.hanson.common.constant.Constant;
import com.hanson.pojo.vo.AuthGroup;
import com.hanson.pojo.vo.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @program: manager
 * @description: 权限表信息包装
 * @param:
 * @author: Hanson
 * @create: 2020-09-20 16:18
 **/
@ApiModel(value = "权限-权限组信息对象",description = "封装权限信息和权限组信息，包含权限的属性和权限组属性信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthsInfo<T> implements Serializable {
    //权限ID
    @ApiModelProperty(name = "id",value = "权限ID",dataType = "Integer")
    private Integer id;
    //权限名称
    @ApiModelProperty(name = "name",value = "权限名称",dataType = "String")
    private String name;
    //资源字段
    @ApiModelProperty(name = "field",value = "资源字段",dataType = "String")
    private String field;
    //所属权限组
    @ApiModelProperty(name = "group",value = "所属权限组",dataType = "String")
    private String group;
    //权限描述
    @ApiModelProperty(name = "details",value = "权限描述",dataType = "String")
    private String details;
    //所有的权限组
    @ApiModelProperty(name = "groups",value = "所有的权限组",dataType = "List")
    private List<T> groups;

    public AuthsInfo(@NonNull Authority authority, AuthGroup group,List<T> groups){
        this.id = authority.getAuthorityId();
        this.name = authority.getAuthorityName();
        this.field = authority.getAuthorityField();
        if (group == null) this.group = Constant.AUTH_DEFAULT;
        else this.group = group.getGroupName();
        this.details = authority.getAuthorityDetails();
        this.groups = groups;
    }
}
