package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 分页信息
 * @param:
 * @author: Hanson
 * @create: 2020-09-08 19:48
 **/
@ApiModel(value = "分页信息对象",description = "封装分页信息属性的类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo implements Serializable {
    //数据总量
    @ApiModelProperty(name = "count",value = "总数据量",dataType = "int")
    private int count;
    //当前页
    @ApiModelProperty(name = "current",value = "当前页码",dataType = "int")
    private int current;
    //当前页数据量
    @ApiModelProperty(name = "count",value = "当前页的数据量",dataType = "int")
    private int sizes;
}
