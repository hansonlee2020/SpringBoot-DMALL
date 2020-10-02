package com.hanson.pojo.dto;


import com.hanson.common.constant.Constant;
import com.hanson.pojo.vo.Logistics;
import com.hanson.pojo.vo.Order;
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
 * @description: 前端发货表，用于返回给前端，进行发货选择
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 12:54
 **/
@ApiModel(value = "订单发货表单对象",description = "返回发货订单信息和物流选择")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliverTable implements Serializable {
    //要发货的订单id
    @ApiModelProperty(name = "oid",value = "订单ID",dataType = "String")
    private String oid;
    //要发货的订单的用户id
    @ApiModelProperty(name = "uid",value = "用户ID",dataType = "String")
    private String uid;
    //可选择的物流公司列表
    @ApiModelProperty(name = "lnames",value = "物流公司列表",dataType = "List")
    private List<String> lnames;
    //该订单的物流单号id，如果没有，默认为0
    @ApiModelProperty(name = "rid",value = "订单ID",dataType = "long")
    private Long rid;

    public DeliverTable(Order order, List<String> list){
        //order为订单对象，list为物流公司列表对象
        this.oid = order.getOrderId();
        this.uid = order.getUserId();
        this.rid = order.getRecordId();
        this.lnames = new ArrayList<>(list);
    }
}
