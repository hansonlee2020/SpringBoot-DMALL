package com.hanson.pojo.dto;

import com.hanson.common.constant.Constant;
import com.hanson.pojo.vo.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: DreamMall
 * @description: 订单表实体类，用于回显订单信息给展示页面
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 17:31
 **/
@ApiModel(value = "订单对象",description = "封装订单信息，用于分页数据显示")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTable implements Serializable {
    //格式化日期为2020-01-01 00:00:00格式
    private static ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(SimpleDateFormat::new);
    //订单id
    @ApiModelProperty(name = "oid",value = "订单ID",dataType = "String")
    private String oid;
    //该订单的用户id
    @ApiModelProperty(name = "uid",value = "该订单的用户ID",dataType = "String")
    private String uid;
    //物流单号id
    @ApiModelProperty(name = "rid",value = "物流单号ID",dataType = "long")
    private Long rid;
    //订单支付金额
    @ApiModelProperty(name = "pay",value = "订单支付金额",dataType = "Double")
    private Double pay;
    //订单的备注
    @ApiModelProperty(name = "notes",value = "订单的备注",dataType = "String")
    private String notes;
    //订单创建时间
    @ApiModelProperty(name = "create_time",value = "订单创建时间",dataType = "String")
    private String create_time;
    //订单支付时间
    @ApiModelProperty(name = "pay_time",value = "订单支付时间",dataType = "String")
    private String pay_time;
    //订单关闭时间
    @ApiModelProperty(name = "close_time",value = "订单关闭时间",dataType = "String")
    private String close_time;
    //订单交付完成时间
    @ApiModelProperty(name = "finish_time",value = "订单交付完成时间",dataType = "String")
    private String finish_time;
    //订单的状态
    @ApiModelProperty(name = "state",value = "订单的状态",dataType = "String")
    private String state;

    public OrderTable(String orderId,
                      Integer userId,
                      Long recordId,
                      Double paymentAmount,
                      String notes,
                      Date createTime,
                      Date payTime,
                      Date closeTime,
                      Date finishTime,
                      Integer orderState,
                      String pattern){
        this.oid = orderId;
        this.uid = String.valueOf(userId);
        this.rid = recordId;
        this.pay = paymentAmount;
        this.notes = notes;
        if (createTime == null) this.create_time = "";
        else this.create_time = setFormatPattern(pattern).format(createTime);
        if (payTime == null) this.pay_time = "";
        else this.pay_time = setFormatPattern(pattern).format(payTime);
        if (closeTime == null) this.close_time = "";
        else this.close_time = setFormatPattern(pattern).format(closeTime);
        if (finishTime == null)this.finish_time ="";
        else this.finish_time = setFormatPattern(pattern).format(finishTime);
        convertState(orderState);
    }
    public OrderTable(Order order,String pattern){
        this.oid = order.getOrderId();
        this.uid = order.getUserId();
        if (order.getRecordId() == null) this.rid = null;
        else this.rid = order.getRecordId();
        if (order.getPaymentAmount() == null) this.pay = 0.0;
        else this.pay = order.getPaymentAmount();
        if (order.getNotes() == null) this.notes = "";
        else this.notes = order.getNotes();
        convertDate(order,pattern);
        convertState(order.getOrderState());
    }
    //设置日期格式
    public SimpleDateFormat setFormatPattern(String pattern){
        SimpleDateFormat dateFormat = THREAD_LOCAL.get();
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }
    //转换state属性
    private void convertState(Integer state){
        if (state == null) this.state = "";
        else if (state == Constant.SUCCESS_STATE) this.state = Constant.SUCCESS;
        else if (state == Constant.RECEIVED_STATE) this.state = Constant.RECEIVED;
        else if (state == Constant.TO_BE_RECEIVED_STATE) this.state = Constant.TO_BE_RECEIVED;
        else if (state == Constant.DELIVERED_STATE) this.state = Constant.DELIVERED;
        else if (state == Constant.PAID_STATE) this.state = Constant.PAID;
        else if (state == Constant.TO_BE_PAID_STATE) this.state = Constant.TO_BE_PAID;
        else if (state == Constant.CLOSE_STATE) this.state = Constant.CLOSE;
        else if (state == Constant.RETURN_STATE) this.state = Constant.RETURN;
        else this.state = Constant.RUBBISH;
    }
    //转换日期
    private void convertDate(Order order,String pattern){
        if (order.getCreateTime() == null) this.create_time = "";
        else this.create_time = setFormatPattern(pattern).format(order.getCreateTime());
        if (order.getPayTime() == null) this.pay_time = "";
        else this.pay_time = setFormatPattern(pattern).format(order.getPayTime());
        if (order.getCloseTime() == null) this.close_time = "";
        else this.close_time = setFormatPattern(pattern).format(order.getCloseTime());
        if (order.getFinishTime() == null) this.finish_time ="";
        else this.finish_time = setFormatPattern(pattern).format(order.getFinishTime());
    }
}
