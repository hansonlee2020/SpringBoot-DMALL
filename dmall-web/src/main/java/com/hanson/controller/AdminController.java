package com.hanson.controller;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.common.exception.ExceptionMessage;
import com.hanson.pojo.dto.AdminEditTable;
import com.hanson.pojo.dto.GroupAuth;
import com.hanson.pojo.dto.SystemUser;
import com.hanson.pojo.dto.UserAuthority;
import com.hanson.pojo.vo.Role;
import com.hanson.pojo.vo.User;
import com.hanson.pojo.vo.UserRole;
import com.hanson.service.RoleService;
import com.hanson.service.UserRoleService;
import com.hanson.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: 管理员管理接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-26 20:25
 **/
@Api(tags = "管理员管理接口")
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

/*q
---------------------------------管理员管理-------------------
 */

    /*q
    --------------------------管理员列表分页--------------------
     */
    //打开管理员列表
    @ApiOperation("打开管理员列表")
    @GetMapping("/lists")
    public String adminLists(){
        return "admin/admin-list";
    }


    //管理员数据分页
    @ApiOperation("管理员列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @PostMapping("/lists")
    @ResponseBody
    public PageResult<List<SystemUser>> adminSplit(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page, String search){
        try {
            return userService.getUserSplit(limit, page, search);
        } catch (NullPointerException e) {
            return new PageResult<>(500,e.getMessage(),0,null);
        } catch (Exception e){
            return new PageResult<>(500,"系统查询出错",0,null);
        }
    }

/*q
-----------------------------------查看管理员角色信息----------------------
*/
    //打开管理员角色页面
    @ApiOperation("打开管理员角色页面")
    @ApiImplicitParam(name = "name",value = "管理员名称",required = true,dataType = "String")
    @GetMapping("/roles/lists")
    public String adminRolesList(String name, Model model){
        model.addAttribute("aname",name);
        return "admin/admin-role";
    }

    //获取所有角色
    @ApiOperation("获取所有已启用的角色")
    @GetMapping("/roles/info")
    @ResponseBody
    public CommonResult<List<Role>> adminRolesInfo(){
        CommonResult<List<Role>> res = roleService.getRoles();
        res.setContent("获取角色信息成功");
        return res;
    }

    //获取管理员角色
    @ApiOperation("获取管理员拥有的角色")
    @ApiImplicitParam(name = "name",value = "管理员名称",required = true,dataType = "String")
    @PostMapping("/roles/has")
    @ResponseBody
    public CommonResult<List<UserRole>> adminRoles(@RequestParam("name") String name){
        try {
            CommonResult<User> temp = userService.getUserByName(name);
            if (temp.getData() != null) {
                User admin = temp.getData();
                CommonResult<List<UserRole>> hasRoles = userService.getUserRoles(admin.getUserId());
                if (hasRoles.getData() == null) throw new NullPointerException("用户没有任何角色");
                return hasRoles;
            }
            return new CommonResult<>(ResultCode.FAILED,null);
        } catch (NullPointerException e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,em.getMsg());
        }
    }
    //修改管理员角色
    @ApiOperation("修改管理员拥有的角色")
    @ApiImplicitParam(name = "request",value = "编辑的表单数据",required = true,dataType = "AdminEditTable")
    @PostMapping("/roles/edit")
    @ResponseBody
    public CommonResult<Integer> adminRolesEdit(HttpServletRequest request){
        String uname = request.getParameter("uname");
        String[] ids = request.getParameterValues("ids");
        try {
            if (ids == null) throw new NullPointerException("至少要选择一个角色");
            Set<Integer> rids = new HashSet<>();
            for (String id : ids) {
                rids.add(Integer.valueOf(id));
            }
            CommonResult<Integer> res = userRoleService.createUserRoles(uname, rids);
            Integer num =  res.getData();
            if (num <= 0) res.setContent("用户添加角色失败");
            else res.setContent("用户" + uname + "成功拥有" + num + "个角色");
            return res;
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }

    }

    /*q
    ----------------------------------查看管理员权限信息------------------------
     */
    //打开管理员的权限信息页面
    @ApiOperation("打开管理员权限信息页面")
    @ApiImplicitParam(name = "name",value = "管理员名称",required = true,dataType = "String")
    @GetMapping("/perms")
    public String adminPermsList(String name,Model model){
        model.addAttribute("name",name);
        return "admin/admin-perms";
    }
    //获取管理员拥有的权限信息
    @ApiOperation("获取管理员拥有的权限信息")
    @ApiImplicitParam(name = "name",value = "管理员名称",required = true,dataType = "String")
    @PostMapping("/perms")
    @ResponseBody
    public CommonResult<List<GroupAuth<UserAuthority>>> adminPermsInfo(@RequestParam("name") String adminName){
        try {
            CommonResult<List<GroupAuth<UserAuthority>>> res = userRoleService.getAuths(adminName);
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

    /*q
    ----------------------------------管理员信息编辑-------------------
     */
    //启用管理员
    @ApiOperation("启用管理员")
    @ApiImplicitParam(name = "uid",value = "需启用的管理员ID",required = true,dataType = "String")
    @PostMapping("/on")
    @ResponseBody
    public CommonResult<Integer> adminOn(String uid){
        CommonResult<Integer> res = userService.updateUserState(uid, Constant.ENABLE_STATE);
        Integer num =  res.getData();
        if (num > 0) res.setContent(Constant.ENABLE);
        return res;
    }
    //禁用管理员
    @ApiOperation("禁用管理员")
    @ApiImplicitParam(name = "uid",value = "需禁用的管理员ID",required = true,dataType = "String")
    @PostMapping("/off")
    @ResponseBody
    public CommonResult<Integer> adminOff(String uid){
        CommonResult<Integer> res = userService.updateUserState(uid, Constant.DISABLE_STATE);
        Integer num = res.getData();
        if (num > 0) res.setContent(Constant.DISABLE);
        return res;
    }
    //删除管理员
    @ApiOperation("删除管理员")
    @ApiImplicitParam(name = "uid",value = "需删除的管理员ID",required = true,dataType = "String")
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> adminDelete(String uid){
        try {
            return userService.deleteUser(uid);
        } catch (Exception e) {
            return new CommonResult<>(ResultCode.FAILED,null);
        }
    }
    //打开修改管理员信息页面
    @ApiOperation("打开修改管理员信息页面")
    @ApiImplicitParam(name = "uid",value = "需修改的管理员ID",required = true,dataType = "String")
    @GetMapping("/update/page")
    public String adminEdit(String uid,Model model){
        model.addAttribute("uid",uid);
        return "admin/admin-edit";
    }
    //获取管理员信息
    @ApiOperation("获取正在编辑的管理员信息")
    @ApiImplicitParam(name = "id",value = "需修改的管理员ID",required = true,dataType = "String")
    @PostMapping("/info")
    @ResponseBody
    public CommonResult<User> adminInfo(@RequestParam("id") String uid){
        try {
            return userService.getUser(uid);
        } catch (Exception e) {
            return new CommonResult<>(ResultCode.FAILED,null);
        }
    }
    //修改管理员信息
    @ApiOperation("修改管理员信息")
    @ApiImplicitParam(name = "request",value = "编辑后的管理员信息表单数据",required = true,dataType = "AdminEditTable")
    @PostMapping("/update")
    @ResponseBody
    public CommonResult<Integer> adminUpdate(HttpServletRequest request){
        String[] roleIds = request.getParameterValues("ids");
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        String uid = request.getParameter("uid");
        String state = request.getParameter("state");
        Integer st = null;
        Set<Integer> rids = new HashSet<>();
        if (state == null | "".equals(state)) st = Constant.DISABLE_STATE;
        else st = Constant.ENABLE_STATE;
        try {
            if (roleIds == null || roleIds.length <= 0) throw new NullPointerException("至少要选择一个角色");
            for (String roleId : roleIds) {
                rids.add(Integer.valueOf(roleId));
            }
            AdminEditTable aet = new AdminEditTable(uid, username, st, pass, rids);
            return userService.updateUser(aet);
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }
    //打开新增管理员页面
    @ApiOperation("打开新增管理员页面")
    @GetMapping("/add/page")
    public String adminAdd(){
        return "admin/admin-add";
    }
    //新增管理员
    @ApiOperation("新增管理员")
    @ApiImplicitParam(name = "request",value = "新增的管理员信息表单数据",required = true,dataType = "HttpServletRequest")
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Integer> adminCreate(HttpServletRequest request){
        String[] roleIds = request.getParameterValues("ids");
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        String uid = request.getParameter("uid");
        String state = request.getParameter("state");
        Integer st = null;
        Set<Integer> rids = new HashSet<>();
        if (state == null | "".equals(state)) st = Constant.DISABLE_STATE;
        else st = Constant.ENABLE_STATE;
        try {
            if (roleIds == null || roleIds.length <= 0) throw new NullPointerException("至少要选择一个角色");
            for (String roleId : roleIds) {
                rids.add(Integer.valueOf(roleId));
            }
            AdminEditTable aet = new AdminEditTable(uid, username, st, pass, rids);
            return userService.createUser(aet);
        } catch (Exception e) {
            ExceptionMessage em = new ExceptionMessage(e);
            return new CommonResult<>(ResultCode.FAILED,null,e.getMessage() + em.getMsg());
        }
    }
}
