package com.hanson.controller;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.exception.DmallException;
import com.hanson.common.exception.ExceptionMessage;
import com.hanson.pojo.vo.GoodsCategory;
import com.hanson.service.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: manager
 * @description: 商品分类接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 18:09
 **/
@Api(tags = "商品分类管理接口")
@Controller
@RequestMapping("/goods/cate")
public class GoodsCategoryController {
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    /*q
    ----------------------------商品分类列表---------
     */

    //跳转到商品分类页面
    @ApiOperation("打开商品分类页面")
    @GetMapping("/page")
    public String goodsCatePage(){
        return "goods/category/goods-category";
    }

    //获取顶级分类
    @ApiOperation("获取顶级分类信息")
    @GetMapping("/top")
    @ResponseBody
    public List<GoodsCategory> goodsTopCate(){
        return goodsCategoryService.getTopGoodsCategories();
    }

    //根据父级分类获取子级分类
    @ApiOperation("获取子级分类信息")
    @ApiImplicitParam(name = "pid",value = "父分类ID",required = true,dataType = "Integer")
    @GetMapping("/child")
    @ResponseBody
    public List<GoodsCategory> goodsChildCate(@RequestParam("pid") Integer parentId){
        return goodsCategoryService.getGoodsCategoriesByParentId(parentId);
    }

    //直接获取全部分类
    @ApiOperation("获取全部分类信息")
    @PostMapping("/all")
    @ResponseBody
    public CommonResult<List<GoodsCategory>> goodsAllCates(){
        try {
            List<GoodsCategory> categories = goodsCategoryService.getGoodsCategories();
            return new CommonResult<>(ResultCode.SUCCESS, categories);
        } catch (NullPointerException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            //记录日志
            return new CommonResult<>(ResultCode.FAILED,null,"加载分类数据出错！");
        }
    }

    //编辑分类
    @ApiOperation("编辑分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid",value = "分类ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "cname",value = "分类名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "pid",value = "所属父分类ID",required = true,dataType = "Integer")
    })
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult<Integer> goodsCateEdit(@RequestParam("cid") Integer cateId,
                                               @RequestParam("cname")String cateName,
                                               @RequestParam("pid")Integer parentId){
        try {
            //检查字符串是否为空字符
            if (cateName == null || "".equals(cateName.replaceAll(" ",""))) throw new DmallException("分类名称不能为空！");
            if (cateId == null){
                //新增
                GoodsCategory cate = new GoodsCategory(null, cateName, parentId);
                return goodsCategoryService.createGoodsCategory(cate);
            }
                //检查父id是否为自己，循环依赖
            if (cateId.equals(parentId)) throw new DmallException("不能设置当前节点为自己的父节点，循环依赖！");
            //编辑
            GoodsCategory cate = new GoodsCategory(cateId, cateName, parentId);
            return goodsCategoryService.updateGoodsCategory(cate);
        } catch (DmallException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            //记录日志
            return new CommonResult<>(ResultCode.FAILED,null,"其他系统异常，请联系管理员！");
        }
    }

    //删除分类
    @ApiOperation("删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid",value = "分类ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "flag",value = "是否确认删除",required = true,dataType = "boolean")
    })
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> deleteGoodsCate(@RequestParam("cid") Integer cateId,boolean flag){
        try {
            if (cateId == null) throw new NullPointerException("要删除的分类id为空，请选择分类！");
            return goodsCategoryService.deleteGoodsCategory(cateId,flag);
        } catch (NullPointerException|DmallException e) {
            ExceptionMessage em = new ExceptionMessage();
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            //日志记录
            return new CommonResult<>(ResultCode.FAILED,null,"系统异常");
        }
    }
}
