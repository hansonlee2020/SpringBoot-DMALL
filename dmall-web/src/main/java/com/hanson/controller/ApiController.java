package com.hanson.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: manager
 * @description: API管理统一接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-29 22:50
 **/
@Api(tags = "API地址接口")
@Controller
@RequestMapping("/api")
public class ApiController {


    //swagger接口文档
    @ApiOperation("swagger接口文档")
    @GetMapping("/swagger")
    public String SwaggerApi(){
        return "redirect:/swagger-ui.html";
    }

    //druid数据源监控接口
    @ApiOperation("druid数据源监控接口")
    @GetMapping("/druid")
    public String DruidApi(){
        return "redirect:/druid/index.html";
    }
}
