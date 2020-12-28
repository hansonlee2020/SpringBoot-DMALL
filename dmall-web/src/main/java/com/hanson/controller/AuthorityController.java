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
import com.hanson.pojo.dto.AuthsInfo;
import com.hanson.pojo.vo.AuthGroup;
import com.hanson.pojo.vo.Authority;
import com.hanson.service.AuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: manager
 * @description: 权限接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-20 15:09
 **/
@Api(tags = "权限管理接口")
@Controller
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;


    /*q
    --------------------------------权限列表-----------------
     */
    //打开权限列表
    @ApiOperation("打开权限列表页面")
    @GetMapping("/lists")
    @WebLogIgnore
    public String authPageOpen(){
        return "authority/auth-list";
    }

    //权限列表分页
    @ApiOperation("权限列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @CacheData(cacheKey = Constant.AUTHORITY_CACHE,dataClass = PageResult.class,expireTime = 7L,timeUnit = TimeUnit.DAYS)
    @PostMapping("/lists")
    @ResponseBody
    public PageResult authSplit(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, String search){
        try {
            return authorityService.splitAuthority(page, limit, search);
        } catch (Exception e) {
            return new PageResult<>(500,e.getMessage(), 0, null);

        }
    }

    /*q
    ---------------------------------------权限编辑-------------
     */

    //打开权限编辑页面
    @ApiOperation("打开编辑权限页面")
    @ApiImplicitParam(name = "aid",value = "权限ID",required = true,dataType = "Integer")
    @GetMapping("/edit/page")
    @WebLogIgnore
    public String authEditPage(@RequestParam("aid") Integer aid, Model model){
        model.addAttribute("aid",aid);
        return "authority/auth-edit";
    }

    //获取权限信息
    @ApiOperation("获取编辑的权限信息")
    @ApiImplicitParam(name = "aid",value = "需编辑的权限ID",required = true,dataType = "Integer")
    @PostMapping("/info")
    @ResponseBody
    public CommonResult<AuthsInfo<AuthGroup>> authInfo(@RequestParam("aid") Integer authId){
        try {
            return authorityService.getAuthorityInfo(authId);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }
    //编辑权限信息
    @ApiOperation("编辑权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid",value = "需编辑的权限ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "name",value = "权限名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "field",value = "资源字段",required = true,dataType = "String"),
            @ApiImplicitParam(name = "details",value = "权限描述",required = true,dataType = "String"),
            @ApiImplicitParam(name = "gid",value = "所属权限组ID",required = true,dataType = "Integer")
    })
    @CacheClear(keys = {Constant.AUTHORITY_CACHE})
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult<Integer> authEdit(@RequestParam("aid") Integer aid,
                                          @RequestParam("name")String authName,
                                          @RequestParam("field")String field,
                                          @RequestParam("details")String details,
                                          @RequestParam("gid")Integer gid){
        Authority authority = new Authority(aid,authName,field,gid,details);
        try {
            return authorityService.updateAuthority(authority);
        } catch (NullPointerException | DmallException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }catch (Exception e){
            return new CommonResult<>(ResultCode.FAILED,null,"权限资源字段已存在，操作失败！");
        }
    }

    //打开新增权限页面
    @ApiOperation("打开新增权限页面")
    @GetMapping("/add/page")
    @WebLogIgnore
    public String authAddPage(){
        return "authority/auth-add";
    }

    //获取权限组信息
    @ApiOperation("获取所有的权限组信息")
    @GetMapping("/g/info")
    @ResponseBody
    public CommonResult<List<AuthGroup>> authGroupInfo(){
        try {
            return authorityService.getAuthGroups();
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }

    //新增权限
    @ApiOperation("新增权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "权限名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "field",value = "资源字段",required = true,dataType = "String"),
            @ApiImplicitParam(name = "details",value = "权限描述",required = true,dataType = "String"),
            @ApiImplicitParam(name = "gid",value = "所属权限组ID",required = true,dataType = "Integer")
    })
    @CacheClear(keys = {Constant.AUTHORITY_CACHE})
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Integer> authCreate(@RequestParam("name")String authName,
                                            @RequestParam("field")String field,
                                            @RequestParam("details")String details,
                                            @RequestParam("gid")Integer gid){
        try {
            if (gid < 0) throw new NullPointerException("未选择权限组");
            Authority authority = new Authority(null, authName, field, gid, details);
            return authorityService.createAuthority(authority);
        } catch (NullPointerException | DmallException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
           return new CommonResult<>(ResultCode.FAILED,null,"权限资源字段已存在，操作失败！");
        }
    }

    //删除权限
    @ApiOperation("删除权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid",value = "权限ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "flag",value = "是否确认删除",required = true,dataType = "boolean")
    })
    @CacheClear(keys = {Constant.AUTHORITY_CACHE})
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> authDelete(@RequestParam("aid") Integer aid, @RequestParam("flag") boolean flag){
        try {
            return authorityService.deleteAuthority(aid, flag);
        } catch (NullPointerException | DmallException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            e.printStackTrace();
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult<>(ResultCode.FAILED,null);
        }
    }
}
