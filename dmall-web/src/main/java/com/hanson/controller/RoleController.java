package com.hanson.controller;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.ExceptionMessage;
import com.hanson.pojo.dto.GroupAuth;
import com.hanson.pojo.dto.RoleAuth;
import com.hanson.pojo.dto.RoleEditTable;
import com.hanson.pojo.dto.UserAuthority;
import com.hanson.pojo.vo.Role;
import com.hanson.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: 角色管理接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-18 19:17
 **/
@Api(tags = "角色管理接口")
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /*q
    --------------------------------------角色列表--------------
     */
    //打开角色管理页面
    @ApiOperation("打开角色管理页面")
    @GetMapping("/lists")
    public String rolesList(){
        return "role/role-list";
    }

    //角色信息分页
    @ApiOperation("角色列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @PostMapping("/lists")
    @ResponseBody
    public PageResult<List<Role>> rolesSplit(@RequestParam("page")Integer currentPage, @RequestParam("limit")Integer limit, String search){
        try {
            return roleService.rolesSplit(currentPage, limit, search);
        } catch (NullPointerException e) {
            return new PageResult<>(500,e.getMessage(),0,null);
        } catch (Exception e){
            return new PageResult<>(500,"系统查询出错",0,null);
        }
    }

    //打开角色权限页面
    @ApiOperation("打开角色权限页面")
    @ApiImplicitParam(name = "rid",value = "角色ID",required = true,dataType = "Integer")
    @GetMapping("/hasperms")
    public String rolePermsPage(@RequestParam("rid") Integer roleId, Model model){
        model.addAttribute("rid",roleId);
        return "role/role-perms";
    }

    //查看角色权限
    @ApiOperation("查看角色拥有权限")
    @ApiImplicitParam(name = "rid",value = "角色ID",required = true,dataType = "Integer")
    @PostMapping("/hasperms")
    @ResponseBody
    public CommonResult<List<GroupAuth<RoleAuth>>> rolePerms(@RequestParam("rid") Integer roleId){
        try {
            return roleService.getRolePerms(roleId);
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }

    /*q
    ------------------------------角色信息编辑--------------------
     */

    //打开角色添加页面
    @ApiOperation("打开角色添加页面")
    @GetMapping("/add/page")
    public String roleOpenAdd(){
        return "role/role-add";
    }

    //获取权限信息
    @ApiOperation("获取所有的权限信息")
    @PostMapping("/perms")
    @ResponseBody
    public CommonResult<List<GroupAuth<UserAuthority>>> permsInfo(){
        try {
            CommonResult<List<GroupAuth<UserAuthority>>> res = roleService.getPerms();
            Object data = res.getData();
            if (data != null){
                if (((List<?>) data).size() <= 0) res.setContent("没有任何<权限组-权限>信息");
            }
            return res;
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }

    //添加角色
    @ApiOperation("添加角色")
    @ApiImplicitParam(name = "request",value = "角色数据表单",required = true,dataType = "HttpServletRequest")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Integer> roleCreate(HttpServletRequest request){
        String rolename = request.getParameter("rolename");
        String state = request.getParameter("state");
        String[] aids = request.getParameterValues("ids");
        int st = 1;
        String rn = rolename.replaceAll(" ","");
        if (state == null || "".equals(state)) st = 0;
        if ("".equals(rn)) throw new NullPointerException("角色名不能为空字符串！");
        Set<Integer> aidset = new HashSet<>();
        if (aids != null && aids.length > 0){
            for (String rid : aids) {
                aidset.add(Integer.valueOf(rid));
            }
        }
        try {
            CommonResult<Integer> res = roleService.createRole(new Role(null, Constant.ENABLE_STATE, rn), aidset);
            Integer num =  res.getData();
            if (num < 0 ) res.setContent(Constant.CREATE_ROLE_FAILED);
            else res.setContent(Constant.CREATE_ROLE_SUCCESS);
            return res;
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }

    //打开编辑角色信息页面
    @ApiOperation("打开编辑角色信息页面")
    @ApiImplicitParam(name = "rid",value = "角色ID",required = true,dataType = "Integer")
    @GetMapping("/edit/page")
    public String roleUpdatePage(@RequestParam("rid") Integer roleId,Model model){
        model.addAttribute("rid",roleId);
        return "role/role-edit";
    }

    //获取角色信息
    @ApiOperation("获取角色信息")
    @ApiImplicitParam(name = "rid",value = "角色ID",required = true,dataType = "Integer")
    @PostMapping("/info")
    @ResponseBody
    public CommonResult<RoleEditTable<GroupAuth<RoleAuth>>> roleInfo(@RequestParam("rid") Integer roleId){
        try {
            return roleService.getRoleInfo(roleId);
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }

    //编辑角色信息
    @ApiOperation("编辑角色")
    @ApiImplicitParam(name = "request",value = "角色数据表单",required = true,dataType = "HttpServletRequest")
    @PostMapping("/edit")
    @ResponseBody
    public CommonResult<Integer> roleUpdate(HttpServletRequest request){
        String roleName = request.getParameter("rolename");
        String state = request.getParameter("state");
        String[] authIds = request.getParameterValues("ids");
        String rn = roleName.replaceAll(" ","");
        int st = 0;
        if ("".equals(rn) ) throw new NullPointerException("角色名不能为空字符串");
        Set<Integer> set = new HashSet<>();
        if (authIds != null && authIds.length >0) for (String authId : authIds) {
            set.add(Integer.valueOf(authId));
        }
        if (state != null) st = 1;
        try {
            RoleEditTable<Integer> roleEditTable = new RoleEditTable<>(null, rn, st, new ArrayList<>(set));
            return roleService.updateRole(roleEditTable);
        } catch (NullPointerException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        } catch (Exception e){
            e.printStackTrace();
            return new CommonResult<>(ResultCode.FAILED,null,"系统错误，更新失败！请查看运行日志。");
        }
    }

    //删除角色
    @ApiOperation("删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "角色ID",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "flag",value = "是否确认删除",required = true,dataType = "boolean")
    })
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> roleDelete(@RequestParam("rid") Integer roleId, @RequestParam("flag")boolean isChecked){
        try {
            CommonResult<Integer> res = roleService.deleteRole(roleId,isChecked);
            if (!("sure".equals(res.getType())))res.setContent(Constant.DELETE_ROLE_SUCCESS);
            return res;
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }
}
