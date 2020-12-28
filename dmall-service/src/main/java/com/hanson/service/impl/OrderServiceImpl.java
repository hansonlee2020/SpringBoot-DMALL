package com.hanson.service.impl;


import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.mapper.*;
import com.hanson.pojo.dto.*;
import com.hanson.pojo.vo.*;
import com.hanson.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @program: DreamMall
 * @description: 订单service层接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 22:31
 **/
@Service
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    private MemberOrderMapper memberOrderMapper;
    @Autowired
    public void setMemberOrderMapper(MemberOrderMapper memberOrderMapper) {
        this.memberOrderMapper = memberOrderMapper;
    }

    private MemberMapper memberMapper;
    @Autowired
    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    private GoodsMapper goodsMapper;
    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    private LogisticsRecordMapper logisticsRecordMapper;
    @Autowired
    public void setLogisticsRecordMapper(LogisticsRecordMapper logisticsRecordMapper) {
        this.logisticsRecordMapper = logisticsRecordMapper;
    }

    private LogisticsMapper logisticsMapper;
    @Autowired
    public void setLogisticsMapper(LogisticsMapper logisticsMapper) {
        this.logisticsMapper = logisticsMapper;
    }

    private LocationMapper locationMapper;
    @Autowired
    public void setLocationMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    private ProvinceMapper provinceMapper;
    @Autowired
    public void setProvinceMapper(ProvinceMapper provinceMapper) {
        this.provinceMapper = provinceMapper;
    }

    private CityMapper cityMapper;
    @Autowired
    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    private AreaMapper areaMapper;
    @Autowired
    public void setAreaMapper(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }

    @Override
    public PageResult<List<OrderTable>> getOrderSplit(Integer pageSize, Integer currentPage, String key ,Integer state) {
        String newKey;//保存格式后的key
        int total;
        int pages;
        if (key != null) {
            newKey = key.replaceAll(" ","");
            total = orderMapper.doSearchOrder(newKey,state).size();
        }
        else {
            newKey = null;
            total = orderMapper.doCounts(state);
        }
        if (total <= 0) throw new NullPointerException("暂无匹配的数据");
        Integer startIndex = (currentPage - 1) * pageSize;
        List<Order> orders = orderMapper.doGetOrderSplit(startIndex, pageSize, newKey,state);
        List<OrderTable> orderTables = new ArrayList<>();
        for (Order order : orders) {
            OrderTable orderTable = new OrderTable(order, Constant.Y_M_D_H_M_S);//转换为前端订单表类
            Member member = memberMapper.doGetMemberById(Integer.valueOf(orderTable.getUid()));
            orderTable.setUid(member.getMemberName());//使用orderTable的userId字段保存用户名，偷梁换柱！
            orderTables.add(orderTable);
        }
        return new PageResult<>(ResultCode.SUCCESS,total,orderTables);
    }

    @Override
    public PageResult<List<OrderTable>> getOrderSplitTrash(Integer pageSize, Integer currentPage, String key, Integer state) {
        String newKey;//保存格式后的key
        int total;
        int pages;
        if (key != null) {
            newKey = key.replaceAll(" ","");
            total = orderMapper.doSearchOrderTrash(newKey,state).size();
        }
        else {
            newKey = null;
            total = orderMapper.doTrashCounts(state);
        }
        if (total <= 0) throw new NullPointerException("暂无匹配的数据");
        Integer startIndex = (currentPage - 1) * pageSize;
        List<Order> orders = orderMapper.doGetOrderSplitTrash(startIndex, pageSize, newKey,state);
        List<OrderTable> orderTables = new ArrayList<>();
        for (Order order : orders) {
            OrderTable orderTable = new OrderTable(order,Constant.Y_M_D_H_M_S);//转换为前端订单表类
            Member member = memberMapper.doGetMemberById(Integer.valueOf(orderTable.getUid()));
            orderTable.setUid(member.getMemberName());//使用orderTable的userId字段保存用户名，偷梁换柱！
            orderTables.add(orderTable);
        }
        return new PageResult<>(ResultCode.SUCCESS,total,orderTables);
    }

    @Override
    public CommonResult<Integer> updateOrderState(String oid, Integer state) {
        Integer num = orderMapper.doUpdateOrder(oid,state);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    public OrderTable getOrderInfo(String oid) {
        Order order = orderMapper.doGetOrderInfoByoid(oid);
        if (order != null){
            OrderTable orderTable = new OrderTable(order,Constant.Y_M_D_H_M_S);
            Member member = memberMapper.doGetMemberById(Integer.valueOf(order.getUserId()));
            orderTable.setUid(member.getMemberName());
            return orderTable;
        }
        return new OrderTable("","",null,0.0,"",null,null,null,null,null);
    }

    @Override
    public CommonResult<Order> getOrderNotes(String oid) {
        Order order = orderMapper.doGetOrderInfoByoid(oid);
        if (order == null) return new CommonResult<>(ResultCode.FAILED,null,"获取备注信息失败");
        return new CommonResult<>(ResultCode.SUCCESS,order);
    }

    @Override
    public CommonResult<Integer> updateOrderNotes(String oid, String notes) {
        String text = notes.replaceAll(" ","");
        if (text.length() <= 0) return new CommonResult<>(ResultCode.FAILED,null,"空字符，备注失败！");
        Integer res = orderMapper.doUpdateOrderNotes(oid, text);
        return res == 1
                ? new CommonResult<>(ResultCode.SUCCESS,null,Constant.NOTES_ORDER_SUCCESS)
                : new CommonResult<>(ResultCode.FAILED,null,Constant.NOTES_ORDER_FAILED);
    }

    @Override
    public CommonResult<DeliverTable> getOrderLogisticsInfo(String oid) {
        Order order = orderMapper.doGetOrderInfoByoid(oid);
        List<Logistics> logisticsList = logisticsMapper.doGetAllLogistics(Constant.ENABLE_STATE);
        if (order != null && logisticsList.size() > 0){
            Member member = memberMapper.doGetMemberById(Integer.valueOf(order.getUserId()));
            order.setUserId(member.getMemberName());
            List<String> lnames = new ArrayList<>();
            for (Logistics l : logisticsList) {
                lnames.add(l.getLogisticsName());
            }
            return new CommonResult<>(ResultCode.SUCCESS,new DeliverTable(order, lnames));
        }
        return new CommonResult<>(ResultCode.SUCCESS,null,ResultCode.FAILED.getContent());
    }

    @Override
    public CommonResult<Integer> batchRecoveryOrders(List<String> ids, Integer state) {
        Set<String> newIds = new HashSet<>(ids);
        Integer num = orderMapper.doBatchUpdateOrder(newIds, state);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    public CommonResult<Integer> batchDeleteOrders(List<String> ids) {
        Set<String> idsSet = new HashSet<>(ids);
        Integer num = orderMapper.doBatchDeleteOrder(idsSet);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    //开启事务,需要更新多个表的信息
    public CommonResult<Integer> deliverOrder(Deliver deliver) {
        Order order = orderMapper.doGetOrderInfoByoid(deliver.getOid());
        if (order == null){
            throw  new NullPointerException("订单号为空");
        }
        //更新订单的物流信息
        Integer res = orderMapper.doUpdateOrderLogistics(deliver.getOid(), deliver.getRid());
        //更新物流信息
        Logistics logistics = logisticsMapper.doGetLogisticsByName(deliver.getLname());
        if (logistics == null) throw new NullPointerException("无法找到物流公司：" + deliver.getLname());
        LogisticsRecord logisticsRecord = new LogisticsRecord(null, deliver.getRid(),logistics.getId(),new Date());
        Integer lres = logisticsRecordMapper.doCreateLogisticsRecord(logisticsRecord);
        //更新订单的状态信息---->已发货
        orderMapper.doUpdateOrder(deliver.getOid(),Constant.DELIVERED_STATE);
        //检查处理结果，反馈处理信息
        String ct = "";
        String lct = "";
        if (res == 1) ct = Constant.DELIVER_ORDER_SUCCESS;
        else ct = Constant.DELIVER_ORDER_FAILED;
        if (lres == 1) lct = Constant.UPDATE_LOGISTICSRECORD_SUCCESS;
        else lct = Constant.UPDATE_LOGISTICSRECORD_FAILED;
        return res == 1 && lres == 1
                ? new CommonResult<>(ResultCode.SUCCESS,null,Constant.DELIVER_ORDER_SUCCESS)
                : new CommonResult<>(ResultCode.FAILED,null,ct + lct);
    }

    @Override
    public CommonResult<OrderDetailsTable> getOrderDetailsForPrint(String oid) {
        Order order = orderMapper.doGetOrderById(oid);//获取订单信息
        OrderTable orderTable = new OrderTable(order,Constant.Y_M_D_H_M_S);//转换为订单表格式,格式化时间数据
        String userId = order.getUserId();
        double subTotal = 0.0;//小计
        double sum = 0.0;//总计
        String notes;//备注
        if (order.getNotes() == null){
            notes = "";
        }else {
            notes = order.getNotes();
        }
        //获取订单的商品列表
        List<MemberOrder> memberOrders = memberOrderMapper.doGetMemberOrderByoid(oid);//订单的商品列表
        Member member = memberMapper.doGetMemberById(Integer.valueOf(userId));//获取用户信息
        List<OrderGoodsTable> orderGoodsTables = new ArrayList<>();//保存订单的商品列表信息
        for (MemberOrder memberOrder : memberOrders) {
            String productId = memberOrder.getProductId();
            Integer quantity = memberOrder.getQuantity();
            Goods goods = goodsMapper.doGetGoodsById(productId);
            Double price = goods.getPrice();
            String productName = goods.getProductName();
            subTotal = quantity*price;
            OrderGoodsTable orderGoodsTable = new OrderGoodsTable(productName, productId, price, quantity, subTotal);
            orderGoodsTables.add(orderGoodsTable);//添加到商品列表
            sum += subTotal;
        }
        //获取用户地址信息
        String address;
        Location location = locationMapper.doGetLocationById(member.getLocationId());
        if (location == null){
            address = "广东省广州市梦想购物中心测试团队";
        }else {
            Province province = provinceMapper.doGetProvinceById(location.getProvinceId());
            City city = cityMapper.doGetCityById(location.getCityId());
            Area area = areaMapper.doGetAreaById(location.getAreaId());
            String addressDetails = location.getAddress();
            //拼接地址
            address = province.getProvinceName() + city.getCityName() + area.getAreaName() + addressDetails;
        }
        OrderDetailsTable detailsTable = new OrderDetailsTable(
                member.getMemberName(),
                orderTable.getCreate_time(),
                order.getOrderId(),
                "在线支付",
                orderTable.getPay_time(),
                "",
                orderTable.getRid(),
                "测试团队",
                member.getMobilePhone(),
                address,
                orderGoodsTables,
                sum,
                notes
        );
        return new CommonResult<>(ResultCode.SUCCESS,detailsTable);
    }

    @Override
    public CommonResult<Integer> batchOrdersRecycle(List<String> ids, Integer abnormalState) {
        Set<String> idsSet = new HashSet<>(ids);
        Integer num = orderMapper.doBatchUpdateOrder(idsSet, abnormalState);
        return new CommonResult<>(ResultCode.SUCCESS,num);
    }
}
