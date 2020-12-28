package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.pojo.dto.*;
import com.hanson.pojo.vo.Order;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 订单service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 22:25
 **/
public interface OrderService {

    /*
    * @description: 分页模糊查询订单信息
    * @params: [pageSize, currentPage, key,abnormalState]
    * pageSize：分页大小, currentPage：当前页数, key：搜索关键字
    * abnormalState：非正常的订单的状态
    * @return: com.hanson.common.api.PageResult<java.util.List<com.hanson.pojo.dto.OrderTable>> 订单数据
    * @Date: 2020/4/24
    */
    public PageResult<List<OrderTable>> getOrderSplit(Integer pageSize, Integer currentPage, String key, Integer abnormalState);



    /*
     * @description: 分页模糊查询回收站订单信息
     * @params: [pageSize, currentPage, key,abnormalState]
     * pageSize：分页大小, currentPage：当前页数, key：搜索关键字
     * abnormalState：回收的订单的状态
     * @return: com.hanson.common.api.PageResult<java.util.List<com.hanson.pojo.dto.OrderTable>> 订单数据
     * @Date: 2020/4/24
     */
    public PageResult<List<OrderTable>> getOrderSplitTrash(Integer pageSize, Integer currentPage, String key, Integer abnormalState);


    /*
    * @description: 根据订单id修改订单状态、回收订单操作
    * @params: [oid, state] oid：订单id, state：订单状态
    * @return: com.hanson.common.api.CommonResult<java.lang.Object> 返回处理消息
    * @Date: 2020/4/25
    */
    public CommonResult<Integer> updateOrderState(String oid, Integer state);



    /*
    * @description: 获取订单信息
    * @params: [oid] 订单id
    * @return: com.hanson.dto.OrderTable
    * @Date: 2020/4/25
    */
    public OrderTable getOrderInfo(String oid);


    /*
    * @description: 获取订单的备注信息
    * @params: [oid]
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/26
    */
    public CommonResult<Order> getOrderNotes(String oid);



    /*
    * @description: 根据订单id修改订单备注
    * @params: [oid, notes] oid：订单id, notes：备注内容
    * @return: com.hanson.common.api.CommonResult<java.lang.Object> 返回处理消息
    * @Date: 2020/4/25
    */
    public CommonResult<Integer> updateOrderNotes(String oid, String notes);



    /*
    * @description: 查询订单信息以及物流公司信息
    * @params: [oid] 订单号
    * @return: com.hanson.common.api.CommonResult<java.lang.Object> 前端发货表对象
    * @Date: 2020/4/25
    */
    public CommonResult<DeliverTable> getOrderLogisticsInfo(String oid);



    /*
    * @description: 订单发货，更新订单的物流信息并且更新订单的状态信息
    * @params: [deliver] 发货对象
    * @return: com.hanson.common.api.CommonResult<java.lang.Object> 返回处理结果
    * @Date: 2020/4/25
    */
    public CommonResult<Integer> deliverOrder(Deliver deliver);



    /*
    * @description: 批量恢复订单
    * @params: [ids] 需要恢复的订单id，需要格式化为set集合
    * @return: com.hanson.common.api.CommonResult<java.lang.Object> 返回处理消息
    * @Date: 2020/4/25
    */
    public CommonResult<Integer> batchRecoveryOrders(List<String> ids, Integer state);


    /*
    * @description:  批量删除订单
    * @params: [ids] 需要删除的订单id，需要格式化为set集合
    * @return: com.hanson.common.api.CommonResult<java.lang.Object> 返回处理消息
    * @throws: Exception
    * @Date: 2020/4/25
    */
    public CommonResult<Integer> batchDeleteOrders(List<String> ids);





    /*
    * @description: 获取订单详细信息
    * @params: [oid] 订单id
    * @return: com.hanson.common.api.CommonResult<com.hanson.pojo.dto.OrderDetailsTable> 订单打印表类
    * @Date: 2020/9/12
    */
    public CommonResult<OrderDetailsTable> getOrderDetailsForPrint(String oid);

    /*
    * @description: 批量回收订单，回收的订单将不会被正常地展示到订单列表中
    * @params: [ids, abnormalState]
    * ids：需要回收的订单集合，abnormalState：回收后的订单的状态
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/12
    */
    public CommonResult<Integer> batchOrdersRecycle(List<String> ids, Integer abnormalState);
}
