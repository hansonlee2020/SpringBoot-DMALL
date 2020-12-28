package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 权限类，保存权限的所有信息,实现Comparable接口，用于排序
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 15:30
 **/
@ApiModel(value = "权限对象",description = "权限表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements Comparable<Authority>, Serializable {
    //权限id
    @ApiModelProperty(name = "authorityId",value = "权限ID",dataType = "Integer")
    private Integer authorityId;
    //权限名称
    @ApiModelProperty(name = "authorityName",value = "权限名称",dataType = "String")
    private String authorityName;
    //权限资源段名
    @ApiModelProperty(name = "authorityField",value = "资源段名",dataType = "String")
    private String authorityField;
    //所在权限组id
    @ApiModelProperty(name = "groupId",value = "所在权限组id",dataType = "Integer")
    private Integer groupId;
    //权限描述
    @ApiModelProperty(name = "authorityDetails",value = "权限描述",dataType = "String")
    private String authorityDetails;

    public Authority(Integer authorityId, String authorityName, String authorityField){
        this(authorityId,authorityName,authorityField,null,null);
    }

    public Authority(Integer authorityId, String authorityName, String authorityField, Integer groupId){
        this(authorityId,authorityName,authorityField,groupId,null);
    }

    public Authority(Integer authorityId, String authorityName, String authorityField, String authorityDetails){
        this(authorityId,authorityName,authorityField,null,authorityDetails);
    }

    /**用于权限信息排序，排序规则为权限id首位升序
     * @param o 比较的权限对象
     * @return  返回结果为正数表示当前对象排靠后，返回结果为负数，表示比较的权限对象排靠后，
     * 返回结果为0，表示两对象并排
     */
    @Override
    public int compareTo(Authority o) {
        return this.authorityId - o.authorityId ;
    }
}
