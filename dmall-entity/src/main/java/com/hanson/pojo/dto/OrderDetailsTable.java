package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: DreamMall
 * @description: 订单全部信息表，用于前端打印订单
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 19:31
 **/
@ApiModel(value = "订单详情对象",description = "封装订单所有详细信息，返回给前端")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsTable implements Serializable,Cloneable {
    //订单的用户名
    @ApiModelProperty(name = "username",value = "订单的用户名",dataType = "String")
    private String username;
    //订单创建时间
    @ApiModelProperty(name = "create_time",value = "订单创建时间",dataType = "String")
    private String create_time;
    //订单的id
    @ApiModelProperty(name = "oid",value = "订单的ID",dataType = "String")
    private String oid;
    //订单的支付方式
    @ApiModelProperty(name = "pay_way",value = "订单的支付方式",dataType = "String")
    private String pay_way;
    //订单的支付时间
    @ApiModelProperty(name = "pay_time",value = "订单的支付时间",dataType = "String")
    private String pay_time;
    //订单交付完成的时间
    @ApiModelProperty(name = "delivery_time",value = "订单交付完成的时间",dataType = "String")
    private String delivery_time;
    //订单的物流单号id
    @ApiModelProperty(name = "record_id",value = "订单的物流单号ID",dataType = "long")
    private Long record_id;
    //收货人
    @ApiModelProperty(name = "receiver",value = "收货人",dataType = "String")
    private String receiver;
    //收货人手机号
    @ApiModelProperty(name = "phone",value = "收货人手机号",dataType = "String")
    private String phone;
    //收货人地址
    @ApiModelProperty(name = "address",value = "收货人地址",dataType = "String")
    private String address;
    //订单的已购买商品列表
    @ApiModelProperty(name = "list",value = "订单的已购买商品列表",dataType = "List<OrderGoodsTable>")
    private List<OrderGoodsTable> list;
    //订单的总支付金额
    @ApiModelProperty(name = "sum_price",value = "订单的总支付金额",dataType = "Double")
    private Double sum_price;
    //订单备注
    @ApiModelProperty(name = "notes",value = "订单备注",dataType = "String")
    private String notes;

    @Override
    public Object clone() throws CloneNotSupportedException {
        OrderDetailsTable clone = (OrderDetailsTable) super.clone();
        List<OrderGoodsTable> objects = new ArrayList<>();
        if (list != null) {
            for (OrderGoodsTable goodsTable : list) {
                OrderGoodsTable o = (OrderGoodsTable) goodsTable.clone();
                objects.add(o);
            }
        }
        clone.setList(objects);
        return clone;
    }
}
