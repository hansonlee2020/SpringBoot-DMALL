package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 系统信息
 * @param:
 * @author: Hanson
 * @create: 2020-09-07 18:42
 **/
@ApiModel(value = "系统基础信息对象",description = "封装系统基础信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfo implements Serializable {
    //服务器地址
    @ApiModelProperty(name = "host",value = "服务器地址",dataType = "String")
    private String host;
    //系统名及版本号
    @ApiModelProperty(name = "system",value = "系统名及版本号",dataType = "String")
    private String system;
    //系统cpu核心数
    @ApiModelProperty(name = "core",value = "系统cpu核心数",dataType = "String")
    private String core;
    //Java虚拟机内存
    @ApiModelProperty(name = "jmemory",value = "Java虚拟机内存",dataType = "String")
    private String jmemory;
    //Java虚拟机可用内存
    @ApiModelProperty(name = "jfreeMemory",value = "Java虚拟机可用内存",dataType = "String")
    private String jfreeMemory;
}
