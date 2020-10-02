package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @program: manager
 * @description: 商品分类
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 17:16
 **/
@ApiModel(value = "商品分类对象",description = "商品分类表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCategory implements Serializable {
    //分类ID
    @ApiModelProperty(name = "categoryId",value = "分类ID",dataType = "Integer")
    private Integer categoryId;
    //分类名称
    @ApiModelProperty(name = "categoryName",value = "分类名称",dataType = "String")
    private String categoryName;
    //父分类ID
    @ApiModelProperty(name = "parentId",value = "父分类ID",dataType = "Integer")
    private Integer parentId;
}
