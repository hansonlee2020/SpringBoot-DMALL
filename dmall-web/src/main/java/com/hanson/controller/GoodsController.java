package com.hanson.controller;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.ExceptionMessage;
import com.hanson.pojo.dto.*;
import com.hanson.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: manager
 * @description: 商品管理接口
 * 处理商品业务,负责接收前端的商品请求，实现数据操作，返回对应商品数据
 * 可以处理商品信息的查看、检索、新增、修改、删除等业务
 * @param:
 * @author: Hanson
 * @create: 2020-09-08 16:00
 **/
@Api(tags = "商品管理接口")
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/t")
    public String test(){
        return "test";
    }

    //打开商品列表页面
    @ApiOperation("打开商品列表页面")
    @GetMapping("/lists")
    public String goodsList(){
        return "goods/goods-list";
    }

    //统计商品数量
    @ApiOperation("统计商品数量")
    @GetMapping("/counts")
    @ResponseBody
    public PageInfo goodsCount(){
        Integer count = goodsService.countGoods();
        return new PageInfo(count,0,0);
    }

    /*q
    -------------------------商品分页------------------
     */

    //接收商品分页查询请求，返回商品分页查询结果
    @ApiOperation("商品列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @PostMapping("/lists")
    @ResponseBody
    public PageResult<List<GoodsTable>> goodsSplit(@RequestParam("limit") Integer pageSize, @RequestParam("page") Integer currentPage, String search){
        PageBean<GoodsTable> goodsSplit = null;
        try {
            if (search == null) goodsSplit = goodsService.getGoodsSplit(pageSize, currentPage);
            else goodsSplit = goodsService.getGoodsSplitWithSearch(pageSize,currentPage,search);
            return new PageResult<>(ResultCode.SUCCESS, goodsSplit.getTotal(), goodsSplit.getList());
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            String tips = "</br>当前分页：" + currentPage + "</br>当前分页大小：" + pageSize;
            //记录日志
            return new PageResult<>(500,e.getMessage(),0,null);
        }
    }

    /*q
    -----------------------商品编辑------------
    */

    //跳转到编辑页面
    @ApiOperation("打开编辑商品页面")
    @ApiImplicitParam(name = "gid",value = "商品ID",required = true,dataType = "String")
    @GetMapping("/edit/page")
    public String toGoodsEdit(@RequestParam("gid") String id , Model model){
        model.addAttribute("gid",id);
        return "goods/goods-edit";
    }

    //获取需要编辑的商品信息
    @ApiOperation("获取编辑的商品信息")
    @ApiImplicitParam(name = "gid",value = "商品ID",required = true,dataType = "String")
    @GetMapping("/info")
    @ResponseBody
    public GoodsEditTable goodsInfo(@RequestParam("gid") String id){
        try {
            return goodsService.getGoodsById(id);
        } catch (Exception e) {
            return null;
        }
    }

    //修改商品信息
    @ApiOperation("修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "name",value = "商品名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "brief",value = "商品简介",required = true,dataType = "String"),
            @ApiImplicitParam(name = "price",value = "单价",required = true,dataType = "Double"),
            @ApiImplicitParam(name = "stock",value = "库存",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "limit",value = "限购数量",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "image",value = "缩略图地址",required = false,dataType = "String"),
            @ApiImplicitParam(name = "details",value = "商品描述",required = true,dataType = "String"),
            @ApiImplicitParam(name = "cid",value = "商品分类ID",required = true,dataType = "Integer")
    })
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult<Integer> goodsEdit(String id,String name,String brief,Double price,
                                Integer stock,Integer limit,String image,String details,
                                Integer cid){
        GoodsEditTable editTable = new GoodsEditTable(id, name, brief, price, stock, limit, String.valueOf(cid), 1, image, details);
        return goodsService.updateGoods(editTable);
    }

    /*q
    ------------------------商品添加--------------
     */

    //自动跳转到添加商品页面
    @ApiOperation("打开添加商品页面")
    @GetMapping("/add/page")
    public String toAddProductPage(){
        return "goods/goods-add";
    }

    //接收前端添加商品信息请求，返回添加处理结果
    @ApiOperation("添加商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "商品ID",required = true,dataType = "String"),
            @ApiImplicitParam(name = "name",value = "商品名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "brief",value = "商品简介",required = true,dataType = "String"),
            @ApiImplicitParam(name = "price",value = "单价",required = true,dataType = "Double"),
            @ApiImplicitParam(name = "stock",value = "库存",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "limit",value = "限购数量",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "image",value = "缩略图地址",required = false,dataType = "String"),
            @ApiImplicitParam(name = "details",value = "商品描述",required = true,dataType = "String"),
            @ApiImplicitParam(name = "cid",value = "商品分类ID",required = true,dataType = "Integer")
    })
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Integer> addGoods(String id, String name, String brief, Double price,
                                          Integer stock, Integer limit, String image, String details,
                                          Integer cid){
        GoodsEditTable editTable = new GoodsEditTable(id, name, brief, price, stock, limit, String.valueOf(cid), 1, image, details);
        return goodsService.addGoods(editTable);
    }

    /*q
    ------------------------商品批量操作----------------
     */

    //批量删除商品
    @ApiOperation("批量删除商品")
    @ApiImplicitParam(name = "ids",value = "商品ID组",required = true,dataType = "List<String>")
    @PostMapping("/dels")
    @ResponseBody
    public CommonResult<Integer> goodsDeletes(@RequestBody List<String> ids){
        if (ids.size() <= 0) return new CommonResult<Integer>(ResultCode.FAILED,null,Constant.DELETES_GOODS_FAILED);
        return goodsService.batchRemoveGoods(ids);
    }

    //批量发布商品
    @ApiOperation("批量发布商品")
    @ApiImplicitParam(name = "ids",value = "商品ID组",required = true,dataType = "List<String>")
    @PostMapping("/rels")
    @ResponseBody
    public CommonResult<Integer> goodsReleases(@RequestBody List<String> ids){
        if (ids.size() <= 0) return new CommonResult<Integer>(ResultCode.FAILED,null,Constant.RELEASES_GOODS_FAILED);
        CommonResult<Integer> res = goodsService.batchUpdateGoods(ids, Constant.RELEASED_STATE);
        res.setContent(Constant.RELEASES_GOODS_SUCCESS);
        return res;
    }

    //批量下架商品
    @ApiOperation("批量下架商品")
    @ApiImplicitParam(name = "ids",value = "商品ID组",required = true,dataType = "List<String>")
    @PostMapping("/offs")
    @ResponseBody
    public CommonResult<Integer> goodsOffShelfs(@RequestBody List<String> ids){
        if (ids.size() <= 0) return new CommonResult<Integer>(ResultCode.FAILED,null,Constant.OFF_SHELFS_GOODS_FAILED);
        CommonResult<Integer> res = goodsService.batchUpdateGoods(ids, Constant.OFF_SHELF_STATE);
        res.setContent(Constant.OFF_SHELFS_GOODS_SUCCESS);
        return res;
    }

    //批量审核商品（将商品状态改为审核中，下架的商品进行单个商品操作时，需提交审核后才能提交发布）
    @ApiOperation("批量审核商品")
    @ApiImplicitParam(name = "ids",value = "商品ID组",required = true,dataType = "List<String>")
    @PostMapping("/exams")
    @ResponseBody
    public CommonResult<Integer> goodsExamines(@RequestBody List<String> ids){
        if (ids.size() <= 0) return new CommonResult<Integer>(ResultCode.FAILED,null,Constant.AUDITINGS_GOODS_FAILED);
        CommonResult<Integer> res = goodsService.batchUpdateGoods(ids, Constant.AUDITING_STATE);
        res.setContent(Constant.AUDITINGS_GOODS_SUCCESS);
        return res;
    }

    /*q
    -------------------------商品单个发布、下架、审核、删除------------------
     */

    //商品发布
    @ApiOperation("商品发布")
    @ApiImplicitParam(name = "id",value = "商品ID",required = true,dataType = "String")
    @PostMapping("/release")
    @ResponseBody
    public CommonResult<Integer> goodsRelease(@RequestParam("id")String id){
        CommonResult<Integer> res = goodsService.updateGoodsState(id, Constant.RELEASED_STATE);
        if (res.getCode() == 200) res.setContent(Constant.RELEASE_GOODS_SUCCESS);
        else if (res.getCode() != -1) res.setContent(ResultCode.FAILED.getContent());
        return res;
    }

    //商品下架
    @ApiOperation("商品下架")
    @ApiImplicitParam(name = "id",value = "商品ID",required = true,dataType = "String")
    @PostMapping("/off")
    @ResponseBody
    public CommonResult<Integer> goodsOffShelf(String id){
        CommonResult<Integer> res = goodsService.updateGoodsState(id, Constant.OFF_SHELF_STATE);
        if (res.getCode() == 200) res.setContent(Constant.OFF_SHELF_GOODS_SUCCESS);
        else if (res.getCode() != -1) res.setContent(ResultCode.FAILED.getContent());
        return res;
    }

    //商品重新审核
    @ApiOperation("商品重新审核")
    @ApiImplicitParam(name = "id",value = "商品ID",required = true,dataType = "String")
    @PostMapping("/examine")
    @ResponseBody
    public CommonResult<Integer> goodsExamine(@RequestParam("id")String id){
        CommonResult<Integer> res = goodsService.updateGoodsState(id, Constant.AUDITING_STATE);
        if (res.getCode() == 200) res.setContent(Constant.AUDITING_GOODS_SUCCESS);
        else if (res.getCode() != -1) res.setContent(ResultCode.FAILED.getContent());
        return res;
    }

    //商品删除
    @ApiOperation("商品删除")
    @ApiImplicitParam(name = "id",value = "商品ID",required = true,dataType = "String")
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> goodsDelete(@RequestParam("id")String id){
        return goodsService.deleteGoods(id);
    }

}
