package com.hanson.controller;

import com.hanson.annotation.CacheClear;
import com.hanson.annotation.CacheData;
import com.hanson.annotation.WebLogIgnore;
import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.ExceptionMessage;
import com.hanson.pojo.dto.Deliver;
import com.hanson.pojo.dto.OrderDetailsTable;
import com.hanson.pojo.dto.OrderTable;
import com.hanson.pojo.vo.Order;
import com.hanson.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: manager
 * @description: 订单接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-12 14:28
 **/
@Api(tags = "订单管理接口")
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /*q
    --------------------------订单列表---------------------
     */

    //打开订单列表页面
    @ApiOperation("打开订单列表页面")
    @GetMapping("/lists")
    @WebLogIgnore
    public String orderLists(){
        return "order/order-list";
    }

    //订单列表分页
    @ApiOperation("订单列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @CacheData(cacheKey = Constant.ORDER_CACHE,dataClass = PageResult.class,expireTime = 7L,timeUnit = TimeUnit.DAYS)
    @PostMapping("/lists")
    @ResponseBody
    public PageResult orderSplit(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page, String search){
        try {
            PageResult<List<OrderTable>> orderSplit = orderService.getOrderSplit(limit, page, search,1000);
            orderSplit.setMsg("订单分页成功");
            return orderSplit;
        } catch (NullPointerException e) {
            return new PageResult<>(500,e.getMessage(),0,null);
        } catch (Exception e){
            return new PageResult<>(500,"系统查询出错",0,null);
        }
    }

    /*q
    -------------------------------批量回收、删除、恢复订单--------------------
     */

    //删除订单
    @ApiOperation("批量删除订单")
    @ApiImplicitParam(name = "ids",value = "订单ID组",required = true,dataType = "List<String>")
    @CacheClear(keys = {Constant.ORDER_CACHE,Constant.ORDERTRASH_CACHE})
    @PostMapping("/dels")
    @ResponseBody
    public CommonResult<Integer> orderDeletes(@RequestBody List<String> ids){
        CommonResult<Integer> res = orderService.batchDeleteOrders(ids);
        Integer num = res.getData();
        if (num > 0 ) res.setContent(Constant.DELETE_ORDER_SUCCESS + ",共清理了" + num + "张垃圾订单");
        else res.setContent(Constant.DELETE_ORDER_SUCCESS);
        return res;
    }

    //回收订单
    @ApiOperation("批量回收订单")
    @ApiImplicitParam(name = "ids",value = "订单ID组",required = true,dataType = "List<String>")
    @CacheClear(keys = {Constant.ORDER_CACHE,Constant.ORDERTRASH_CACHE})
    @PostMapping("/recs")
    @ResponseBody
    public CommonResult<Integer> orderRecycles(@RequestBody List<String> ids){
        CommonResult<Integer> res = orderService.batchOrdersRecycle(ids, Constant.RUBBISH_STATE);
        Integer num = res.getData();
        if (num > 0) res.setContent(Constant.RECYCLES_ORDER_SUCCESS + ",成功回收了 " + res.getData() + " 张订单");
        else res.setContent(Constant.RECYCLES_ORDER_FAILED);
        return res;
    }

    //恢复订单
    @ApiOperation("批量恢复订单")
    @ApiImplicitParam(name = "ids",value = "订单ID组",required = true,dataType = "List<String>")
    @CacheClear(keys = {Constant.ORDER_CACHE,Constant.ORDERTRASH_CACHE})
    @PostMapping("/recoveries")
    @ResponseBody
    public CommonResult<Integer> orderRecoveries(@RequestBody List<String> ids){
        CommonResult<Integer> res = orderService.batchRecoveryOrders(ids, Constant.CLOSE_STATE);
        Integer num =  res.getData();
        if (num > 0) res.setContent(Constant.RECOVERIES_ORDER_SUCCESS + ",共恢复了 " + num + " 张订单");
        else {
            res.setType("failed");
            res.setContent(Constant.RECOVERIES_ORDER_FAILED);
        }
        return res;
    }

    /*q
    ------------------------------单个订单删除、回收、发货、恢复、备注-------------
     */

    //删除订单
    @ApiOperation("删除订单")
    @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String")
    @CacheClear(keys = {Constant.ORDER_CACHE,Constant.ORDERTRASH_CACHE})
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> orderDelete(@RequestParam("oid") String oid){
        //由于只提供了批量删除的接口，只能进行参数转换了
        List<String> idList = new ArrayList<>();
        idList.add(oid);
        CommonResult<Integer> res = orderService.batchDeleteOrders(idList);
        Integer num = res.getData();
        if (num > 0) res.setContent(Constant.DELETE_ORDER_SUCCESS);
        else res.setContent(Constant.DELETE_ORDER_FAILED);
        return res;
    }

    //回收订单
    @ApiOperation("回收订单")
    @ApiImplicitParam(name = "id",value = "订单ID",required = true,dataType = "String")
    @CacheClear(keys = {Constant.ORDER_CACHE,Constant.ORDERTRASH_CACHE})
    @PostMapping("/rec")
    @ResponseBody
    public CommonResult<Integer> orderRecycle(String id){
        CommonResult<Integer> res;
        try {
            res = orderService.updateOrderState(id, Constant.RUBBISH_STATE);
            Integer num = res.getData();
            if (num > 0) res.setContent(Constant.RECYCLE_ORDER_SUCCESS);
            else res.setContent(Constant.RECYCLE_ORDER_FAILED);
            return res;
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,em.getMsg());
        }
    }

    //打开发货页面
    @ApiOperation("打开发货页面")
    @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String")
    @GetMapping("/deliver/page")
    @WebLogIgnore
    public String openDeliver(String oid,Model model){
        model.addAttribute("oid",oid);
        return "order/order-deliver";
    }

    //获取物流名称信息
    @ApiOperation("获取订单及物流名称信息")
    @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String")
    @PostMapping("/logistics/info")
    @ResponseBody
    public CommonResult<com.hanson.pojo.dto.DeliverTable> orderLogisticsInfo(@RequestParam("oid") String oid){
        try {
            return orderService.getOrderLogisticsInfo(oid);
        } catch (Exception e) {
            return new CommonResult<>(ResultCode.FAILED,null);
        }
    }

    //发货
    @ApiOperation("订单发货")
    @ApiImplicitParam(name = "deliver",value = "订单数据表单",required = true,dataType = "Deliver")
    @CacheClear(keys = {Constant.ORDER_CACHE})
    @PostMapping("/deliver")
    @ResponseBody
    public CommonResult<Integer> orderDeliver(Deliver deliver){
        CommonResult<Integer> res;
        try {
            res = orderService.deliverOrder(deliver);
            return res;
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }

    //获取订单备注信息
    @ApiOperation("获取订单备注信息")
    @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String")
    @PostMapping("/note/info")
    @ResponseBody
    public CommonResult<Order> orderNotesInfo(@RequestParam("oid") String oid){
        try {
            return orderService.getOrderNotes(oid);
        } catch (Exception e) {
            return new CommonResult<>(ResultCode.FAILED,null);
        }
    }

    //订单备注
    @ApiOperation("订单备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "note",value = "备注信息",required = true,dataType = "String")
    })
    @CacheClear(keys = {Constant.ORDER_CACHE})
    @PostMapping("/note")
    @ResponseBody
    public CommonResult<Integer> orderUpdateNotes(@RequestParam("oid") String oid,@RequestParam("note")String notes){
        try {
            return orderService.updateOrderNotes(oid, notes);
        } catch (Exception e) {
            return new CommonResult<>(ResultCode.FAILED,null,"系统执行出错");
        }
    }

    //恢复订单
    @ApiOperation("恢复订单")
    @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String")
    @CacheClear(keys = {Constant.ORDER_CACHE,Constant.ORDERTRASH_CACHE})
    @PostMapping("/recovery")
    @ResponseBody
    public CommonResult<Integer> orderRecovery(@RequestParam("oid") String oid){
        CommonResult<Integer> res = orderService.updateOrderState(oid, Constant.CLOSE_STATE);
        Integer num = res.getData();
        if (num == 0) res.setContent(Constant.RECOVERY_ORDER_FAILED);
        else res.setContent(Constant.RECOVERY_ORDER_SUCCESS);
        return res;
    }

    //打开订单详情页
    @ApiOperation("打开订单详情页")
    @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String")
    @GetMapping("/details")
    @WebLogIgnore
    public String orderDetailsPage(@RequestParam("oid") String oid,Model model){
        model.addAttribute("oid",oid);
        return "order/order-details";
    }

    //订单详情
    @ApiOperation("查看订单详情")
    @ApiImplicitParam(name = "oid",value = "订单ID",required = true,dataType = "String")
    @PostMapping("/details")
    @ResponseBody
    public CommonResult<OrderDetailsTable> orderDetails(@RequestParam("oid") String oid){
        try {
            return orderService.getOrderDetailsForPrint(oid);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult<>(ResultCode.FAILED,null);
        }
    }

    /*q
    ------------------------------订单垃圾回收站------------------
     */

    //打开订单回收站
    @ApiOperation("打开订单回收站")
    @GetMapping("/recycle/bin")
    @WebLogIgnore
    public String orderRubbishBin(){
        return "order/order-bin";
    }

    //分页获取回收站数据
    @ApiOperation("订单回收站列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @CacheData(cacheKey = Constant.ORDERTRASH_CACHE,dataClass = PageResult.class,expireTime = 7L,timeUnit = TimeUnit.DAYS)
    @PostMapping("/recycle/bin")
    @ResponseBody
    public PageResult orderBinSplit(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page, String search){
        try {
            PageResult<List<OrderTable>> orderSplit = orderService.getOrderSplitTrash(limit, page, search,Constant.RUBBISH_STATE);
            long count = orderSplit.getCount();
            if (count <= 0) orderSplit.setMsg("没有订单数据");
            else orderSplit.setMsg("订单分页成功");
            return orderSplit;
        } catch (NullPointerException e) {
            return new PageResult<>(500,e.getMessage(),0,null);
        } catch (Exception e){
            return new PageResult<>(500,"系统查询出错",0,null);
        }
    }
}
