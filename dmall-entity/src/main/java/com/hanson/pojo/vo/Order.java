package com.hanson.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: DreamMall
 * @description: 订单表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 15:42
 **/
@ApiModel(value = "订单对象",description = "订单表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    //订单号id
    @ApiModelProperty(name = "orderId",value = "订单号ID",dataType = "String")
    private String orderId;
    //用户id
    @ApiModelProperty(name = "userId",value = "用户ID",dataType = "String")
    private String userId;
    //物流号
    @ApiModelProperty(name = "recordId",value = "物流号ID",dataType = "Long")
    private Long recordId;
    //订单的支付金额
    @ApiModelProperty(name = "paymentAmount",value = "支付金额",dataType = "String")
    private Double paymentAmount;
    //订单备注
    @ApiModelProperty(name = "notes",value = "订单备注",dataType = "String")
    private String notes;
    //订单创建时间
    @ApiModelProperty(name = "createTime",value = "订单创建时间",dataType = "Date")
    private Date createTime;
    //订单的支付时间
    @ApiModelProperty(name = "payTime",value = "订单的支付时间",dataType = "Date")
    private Date payTime;
    //订单的关闭时间
    @ApiModelProperty(name = "closeTime",value = "订单的关闭时间",dataType = "Date")
    private Date closeTime;
    //订单交付完成时间
    @ApiModelProperty(name = "finishTime",value = "订单交付完成时间",dataType = "Date")
    private Date finishTime;
    //订单的状态
    @ApiModelProperty(name = "orderState",value = "订单的状态",dataType = "Integer")
    private Integer orderState;
}
