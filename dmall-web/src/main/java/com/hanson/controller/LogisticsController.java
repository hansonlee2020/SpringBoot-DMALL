package com.hanson.controller;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.DmallException;
import com.hanson.common.exception.ExceptionMessage;
import com.hanson.pojo.vo.Logistics;
import com.hanson.service.LogisticsService;
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
 * @description: 物流公司信息接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-25 10:51
 **/
@Api(tags = "物流管理接口")
@Controller
@RequestMapping("/logistics")
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    /*q
    ------------------------物流公司信息列表--------------
     */

    //打开物流信息列表
    @ApiOperation("打开物流信息列表页面")
    @GetMapping("/lists")
    public String logisticsListsPage(){
        return "logistics/logistics-list";
    }

    //信息数据分页
    @ApiOperation("物流公司列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @PostMapping("/lists")
    @ResponseBody
    public PageResult<List<Logistics>> logisticsSplit(@RequestParam("page") Integer page, @RequestParam("limit")Integer limit, String search){
        try {
            return logisticsService.getLogisticsSplit(page, limit, search);
        } catch (NullPointerException e) {
            return new PageResult<>(500,e.getMessage(),0,null);
        } catch (Exception e){
            return new PageResult<>(500,"系统查询出错",0,null);
        }
    }

    /*q
    ----------------------------------物流编辑---------------------
     */

    //打开添加物流页面
    @ApiOperation("打开添加物流页面")
    @GetMapping("/add/page")
    public String logisticsCreatePage(){
        return "logistics/logistics-add";
    }

    //添加物流
    @ApiOperation("添加物流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lname",value = "物流公司名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "sort",value = "排序优先值",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "state",value = "是否启用",required = false,dataType = "String")
    })
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Integer> logisticsCreate(@RequestParam("lname") String logisticsName, @RequestParam("sort") Integer sort, String state){
        try {
            if (logisticsName == null || "".equals(logisticsName.replaceAll(" ",""))) throw new NullPointerException("物流名称为空");
            int st = 0;
            if ("on".equals(state)) st = 1;
            if (sort < 0) sort = Constant.SORT_DEFAULT;
            String name = logisticsName.replaceAll(" ","");
            Logistics logistics = new Logistics(null, name, sort, st);
            return logisticsService.createLogistics(logistics);
        } catch (NullPointerException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage()+ em.getMsg());
        } catch (Exception e){
            return new CommonResult<>(ResultCode.FAILED,null,"系统执行添加出错！");
        }
    }

    //打开编辑物流页面
    @ApiOperation("打开编辑物流页面")
    @ApiImplicitParam(name = "lid",value = "物流公司ID",required = true,dataType = "Integer")
    @GetMapping("/edit/page")
    public String logisticsEditPage(@RequestParam("lid") Integer logisticsId, Model model){
        model.addAttribute("lid",logisticsId);
        return "logistics/logistics-edit";
    }

    //获取物流信息
    @ApiOperation("获取编辑的物流信息")
    @ApiImplicitParam(name = "lid",value = "物流公司ID",required = true,dataType = "Integer")
    @PostMapping("/info")
    @ResponseBody
    public CommonResult<Logistics> logisticsInfo(@RequestParam("lid")Integer logisticsId){
        return logisticsService.getLogisticsById(logisticsId);
    }

    //编辑物流
    @ApiOperation("编辑物流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lid",value = "物流公司ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "lname",value = "物流公司名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "sort",value = "排序优先值",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "state",value = "是否启用",required = false,dataType = "String")
    })
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult<Integer> logisticsUpdate(@RequestParam("lid") Integer logisticsId,
                                                 @RequestParam("lname") String logisticsName,
                                                 @RequestParam("sort") Integer sort,
                                                 String state){
        try {
            if (logisticsName == null || "".equals(logisticsName.replaceAll(" ",""))) throw new NullPointerException("物流名称为空");
            String name = logisticsName.replaceAll(" ", "");
            int st = 0;
            if ("on".equals(state)) st = 1;
            Logistics logistics = new Logistics(logisticsId, name, sort, st);
            return logisticsService.updateLogistics(logistics);
        } catch (NullPointerException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            //记录日志
            return new CommonResult<>(ResultCode.FAILED,null,"系统执行更新出错！");
        }
    }

    //删除物流
    @ApiOperation("删除物流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lid",value = "物流公司ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "flag",value = "是否确认删除",required = true,dataType = "boolean")
    })
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> logisticsDelete(@RequestParam("lid") Integer logisticsId, @RequestParam("flag") boolean flag){
        try {
            Logistics del = new Logistics(logisticsId, null, null, null);
            return logisticsService.deleteLogistics(del, flag);
        } catch (NullPointerException | DmallException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            //记录日志
            return new CommonResult<>(ResultCode.FAILED,null,"系统执行删除出错！");
        }
    }
}
