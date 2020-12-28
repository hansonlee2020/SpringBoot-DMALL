package com.hanson.controller;

import com.hanson.annotation.CacheData;
import com.hanson.annotation.WebLogIgnore;
import com.hanson.common.api.PageResult;
import com.hanson.common.constant.Constant;
import com.hanson.service.WebLogService;
import com.hanson.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @CLassName WebLogController
 * @Description 接口操作日志控制器
 * @Author li hao xin
 * @Date 2020/12/1 10:48
 **/
@Api(value = "接口操作日志控制器")
@Slf4j
@Controller
@RequestMapping("/webLog")
public class WebLogController {

    @Autowired
    private WebLogService webLogService;

    //打开管理员列表
    @ApiOperation("打开操作日志列表")
    @GetMapping("/lists")
    @WebLogIgnore
    public String webLogLists(){
        return "web/weblog/weblog-list";
    }


    @ApiOperation("接口操作日志列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String"),
            @ApiImplicitParam(name = "start",value = "搜索日志开始时间",required = false,dataType = "Date"),
            @ApiImplicitParam(name = "end",value = "搜索日志结束时间",required = false,dataType = "Date")
    })
    @CacheData(cacheKey = Constant.WEBLOG_CACHE,dataClass = PageResult.class,expireTime = 7L,timeUnit = TimeUnit.DAYS)
    @WebLogIgnore
    @PostMapping("/lists")
    @ResponseBody
    public PageResult webLogListPage(PageUtil pageUtil, String search, Date start, Date end){
        PageResult pageResult = PageResult.pageSuccessResult();
        pageUtil.setSizeAndStart();
        webLogService.webLogSplit(pageUtil,search,start,end,pageResult);
        return pageResult;
    }
}
