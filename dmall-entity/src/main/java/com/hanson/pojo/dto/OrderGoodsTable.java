package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 订单的商品列表信息
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 19:37
 **/
@ApiModel(value = "订单商品列表对象",description = "封装订单的商品列表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoodsTable implements Serializable ,Cloneable{
    //商品名称
    @ApiModelProperty(name = "productName",value = "商品名称",dataType = "String")
    private String productName;
    //商品id
    @ApiModelProperty(name = "productId",value = "商品ID",dataType = "String")
    private String productId;
    //商品价格
    @ApiModelProperty(name = "price",value = "商品价格",dataType = "Double")
    private Double price;
    //商品购买数量
    @ApiModelProperty(name = "quantity",value = "商品购买数量",dataType = "Integer")
    private Integer quantity;
    //商品总价
    @ApiModelProperty(name = "subTotal",value = "商品总价",dataType = "Double")
    private Double subTotal;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
