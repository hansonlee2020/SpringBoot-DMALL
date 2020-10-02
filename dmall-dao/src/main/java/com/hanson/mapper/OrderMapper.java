package com.hanson.mapper;

import com.hanson.pojo.vo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 订单表接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 16:26
 **/
@Mapper
@Repository
public interface OrderMapper {

    /*
    * @description: 获取全部订单
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Order>
    * @Date: 2020/4/24
    */
    public List<Order> doGetOrders();


    /*
    * @description: 根据订单id查询订单全部信息
    * @params: [oid] 要查询的订单id
    * @return: com.hanson.pojo.Order 订单对象
    * @Date: 2020/4/24
    */
    public Order doGetOrderById(@Param("orderId") String oid);


    /*
    * @description: 根据用户id查询订单
    * @params: [uid] 要查询的用户id
    * @return: com.hanson.pojo.Order 订单对象
    * @Date: 2020/4/24
    */
    public Order doGetOrderByUid(@Param("userId") String uid);



    /*
    * @description: 分页模糊查询订单，模糊查询列为订单id
    * @params: [startIndex, pageSize, searchKey，state]
    * startIndex：开始索引, pageSize：页面大小, searchKey：查询关键字
    * ,state：非正常的订单状态，逻辑里面进行了【取反】
    * @return: java.util.List<com.hanson.pojo.Order> 订单对象list集合
    * @Date: 2020/4/24
    */
    public List<Order> doGetOrderSplit(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize, @Param("searchKey") String key,@Param("state") @NotNull Integer state);


    /*
    * @description: 模糊查询订单id列
    * @params: [key,state] 查询关键字,state：订单状态
    * @return: java.util.List<com.hanson.pojo.Order> 订单对象list集合
    * @Date: 2020/4/24
    */
    public List<Order> doSearchOrder(@Param("searchKey") String key,@Param("state")@NotNull Integer state);



    /*
    * @description: 创建新订单
    * @params: [order] 订单对象
    * @return: java.lang.Integer  创建成功返回1，否则返回0或抛出异常
    * @throws: Exception SQL执行异常 违反唯一约束
    * @Date: 2020/4/24
    */
    public Integer doCreateOrder(Order order);



    /*
    * @description: 根据订单id修改订单状态信息
    * @params: [oid,state] 需要修改的订单id，state：需要修改的状态
    * @return: java.lang.Integer 修改成功返回1，否则返回0或抛出异常
    * @throws: Exception SQL执行异常，违法唯一约束
    * @Date: 2020/4/24
    */
    public Integer doUpdateOrder(@Param("orderId") String oid, @Param("state")@NotNull Integer state);



    /*
    * @description: 批量更新订单状态
    * @params: [ids，state] 需要更新的订单id集合，state：需要修改的状态
    * @return: java.lang.Integer 返回成功数量
    * @Date: 2020/4/25
    */
    public Integer doBatchUpdateOrder(@Param("ids") Set<String> ids,@Param("state") @NotNull Integer state);



    /*
    * @description:  根据订单id修改订单备注
    * @params: [oid，notes] oid：订单id，notes：备注内容
    * @return: java.lang.Integer 修改成功返回1，否则返回0
    * @Date: 2020/4/25
    */
    public Integer doUpdateOrderNotes(@Param("orderId") String oid, @Param("notes") String notes);


    /*
    * @description: 根据订单id删除订单
    * @params: [ids] 需要删除的订单id集合
    * @return: java.lang.Integer 删除成功返回数量，否则返回0
    * @Date: 2020/4/24
    */
    public Integer doBatchDeleteOrder(@Param("ids") Set<String> ids);



    /*
    * @description: 分页模糊查询 ‘回收站’ 订单信息，模糊查询列为订单id
    * @params: [startIndex, pageSize, key,state] startIndex：开始索引, pageSize：页面大小, searchKey：查询关键字,state：已回收订单的状态
    * @return: java.util.List<com.hanson.pojo.Order> 订单对象list集合
    * @Date: 2020/4/24
    */
    public List<Order> doGetOrderSplitTrash(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize, @Param("searchKey") String key,@Param("state")@NotNull Integer state);


    /*
    * @description: 模糊查询回收的订单 ，模糊查询列为订单id
    * @params: [key，state] 查询关键字，state：已回收订单的状态
    * @return: java.util.List<com.hanson.pojo.Order> 订单对象list集合
    * @Date: 2020/4/24
    */
    public List<Order> doSearchOrderTrash(@Param("searchKey") String key,@Param("state")@NotNull Integer state);


    /*
    * @description: 获取订单部分信息
    * @params: [oid] 订单id
    * @return: com.hanson.pojo.Order
    * @Date: 2020/4/25
    */
    public Order doGetOrderInfoByoid(@Param("orderId") String oid);



    /*
    * @description: 根据订单号更新订单物流号
    * @params: [oid, lid] oid：订单号, lid：物流号
    * @return: java.lang.Integer
    * @Date: 2020/4/25
    */
    public Integer doUpdateOrderLogistics(@Param("orderId") String oid, @Param("recordId") Long recordId);



    /*
    * @description: 初始化订单表
    * @params: []
    * @return: java.lang.Integer
    * @Date: 2020/5/2
    */
    public Integer doInitOrder();


    /*
    * @description: 统计订单数据量
    * @params: [state]非正常订单的状态，里面逻辑使用了【取反】
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/12
    */
    public Integer doCounts(@Param("state")@NotNull Integer abnormalState);

    /*
    * @description: 统计回收的订单数据量
    * @params: [state]已被回收的订单的状态
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/12
    */
    public Integer doTrashCounts(@Param("state")@NotNull Integer state);


    /*
    * @description: 根据物流记录ID获取订单
    * @params: [recordId] 物流记录ID
    * @return: com.hanson.pojo.vo.Order
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Order doGetOrderByRecordId(@Param("rid") Long recordId);
}
