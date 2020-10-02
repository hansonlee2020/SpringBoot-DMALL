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
 * @description: 用户订单表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 16:00
 **/
@ApiModel(value = "会员订单对象",description = "会员订单表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberOrder implements Serializable {
    //主键id
    @ApiModelProperty(name = "id",value = "主键ID",dataType = "Integer")
    private Integer id;
    //用户订单的id
    @ApiModelProperty(name = "orderId",value = "用户订单的ID",dataType = "String")
    private String orderId;
    //该订单的用户id
    @ApiModelProperty(name = "userId",value = "该订单的用户ID",dataType = "String")
    private String userId;
    //订单的每个商品id
    @ApiModelProperty(name = "productId",value = "订单的每个商品ID",dataType = "String")
    private String productId;
    //订单的每个商品对应的商品数量
    @ApiModelProperty(name = "quantity",value = "商品数量",dataType = "Integer")
    private Integer quantity;
}
