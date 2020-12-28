package com.hanson.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 用于接收前端的发货对象
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 13:05
 **/
@ApiModel(value = "订单发货表单对象",description = "接收前端发货订单信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deliver implements Serializable {
    //要发货的订单id
    @ApiModelProperty(name = "oid",value = "订单ID",dataType = "String")
    private String oid;
    //要发货的订单的用户id
    @ApiModelProperty(name = "uid",value = "用户ID",dataType = "String")
    private String uid;
    //物流公司名
    @ApiModelProperty(name = "lname",value = "物流公司名",dataType = "String")
    private String lname;
    //物流单id号
    @ApiModelProperty(name = "rid",value = "物流单ID",dataType = "long")
    private Long rid;
}
