package com.hanson.controller;

import com.hanson.annotation.CacheClear;
import com.hanson.annotation.CacheData;
import com.hanson.annotation.WebLogIgnore;
import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.DmallException;
import com.hanson.common.exception.ExceptionMessage;
import com.hanson.pojo.vo.AuthGroup;
import com.hanson.service.AuthGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @program: manager
 * @description: 权限组接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 13:33
 **/
@Api(tags = "权限分类管理接口")
@Controller
@RequestMapping("/authority/group")
public class AuthGroupController {
    @Autowired
    private AuthGroupService authGroupService;

    /*q
    --------------------------------权限组列表------
     */

    //打开权限组列表页面
    @ApiOperation("打开权限组列表页面")
    @GetMapping("/lists")
    @WebLogIgnore
    public String authGroupPage(){
        return "authority/group-auth-list";
    }

    //权限组列表数据分页
    @ApiOperation("权限组列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @CacheData(cacheKey = Constant.AUTHORITY_GROUP_CACHE,dataClass = PageResult.class,expireTime = 7L,timeUnit = TimeUnit.DAYS)
    @PostMapping("/lists")
    @ResponseBody
    public PageResult authGroupSplit(@RequestParam("page") Integer page, @RequestParam("limit")Integer limit, String search){
        try {
            return authGroupService.authGroupSplit(page, limit, search);
        } catch (NullPointerException e) {
            return new PageResult<>(500,e.getMessage(),0,null);
        } catch (Exception e){
            return new PageResult<>(500,"系统查询出错",0,null);
        }
    }

    /*q
    ------------------------------权限组信息编辑---------------
     */

    //打开编辑权限组页面
    @ApiOperation("打开编辑权限组页面")
    @ApiImplicitParam(name = "groupId",value = "权限组ID",required = true,dataType = "Integer")
    @GetMapping("/edit/page")
    @WebLogIgnore
    public String authGroupEditPage(@RequestParam("gid") Integer groupId, Model model){
        model.addAttribute("gid", groupId);
        return "authority/group-auth-edit";
    }

    //获取权限组信息
    @ApiOperation("获取编辑的权限组信息")
    @ApiImplicitParam(name = "groupId",value = "权限组ID",required = true,dataType = "Integer")
    @CacheClear(keys = {Constant.AUTHORITY_GROUP_CACHE})
    @PostMapping("/info")
    @ResponseBody
    public CommonResult<AuthGroup> authGroupInfo(@RequestParam("gid") Integer groupId){
        try {
            return authGroupService.authGroupInfo(groupId);
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }

    //编辑权限组信息
    @ApiOperation("编辑权限组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId",value = "权限组ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "groupName",value = "权限组名称",required = true,dataType = "Integer")
    })
    @CacheClear(keys = {Constant.AUTHORITY_GROUP_CACHE})
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult<Integer> authGroupUpdate(@RequestParam("gid") Integer groupId, @RequestParam("gname") String groupName){
        try {
            if (groupName == null || "".equals(groupName.replaceAll(" ",""))) throw new DmallException("权限组不能为空字符");
            String gn = groupName.replaceAll(" ","");
            return authGroupService.authGroupUpdate(groupId, gn);
        } catch (DmallException | NullPointerException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            //SQL异常，不能把异常信息直接返回，会暴露sql
            return new CommonResult<>(ResultCode.FAILED,null,"权限组名称重复，修改失败！");
        }
    }

    //打开新增权限组页面
    @ApiOperation("打开新增权限组页面")
    @GetMapping("/add/page")
    @WebLogIgnore
    public String authGroupAddPage(){
        return "authority/group-auth-add";
    }

    //新增权限组
    @ApiOperation("新增权限组信息")
    @ApiImplicitParam(name = "gname",value = "权限组名称",required = true,dataType = "Integer")
    @CacheClear(keys = {Constant.AUTHORITY_GROUP_CACHE})
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Integer> authGroupCreate(@RequestParam("gname") String groupName){
        try {
            if (groupName == null || "".equals(groupName.replaceAll(" ",""))) throw new NullPointerException("权限组名称不能为空字符");
            String gn = groupName.replaceAll(" ","");
            return authGroupService.authGroupCreate(gn);
        } catch (NullPointerException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            return new CommonResult<>(ResultCode.FAILED,null,"权限组名称已存在");
        }
    }

    //删除权限组
    @ApiOperation("删除权限组信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gid",value = "权限组ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "flag",value = "是否确认删除",required = true,dataType = "boolean")
    })
    @CacheClear(keys = {Constant.AUTHORITY_GROUP_CACHE})
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> authGroupDelete(@RequestParam("gid") Integer groupId, boolean flag){
        try {
            return authGroupService.authGroupDeleteById(groupId, flag);
        } catch (NullPointerException|DmallException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            //SQL异常
            // 记录日志
            return new CommonResult<>(ResultCode.FAILED,null,"SQL执行异常，删除失败！");
        }
    }
}
