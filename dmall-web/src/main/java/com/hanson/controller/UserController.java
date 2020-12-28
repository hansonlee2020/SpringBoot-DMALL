package com.hanson.controller;

import com.hanson.annotation.UserLogin;
import com.hanson.annotation.WebLogIgnore;
import com.hanson.pojo.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: manager
 * @description: 后台用户登陆控制层接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-04 18:01
 **/
@Api(tags = "后台登录接口")
@Controller
public class UserController {

/*q
---------------------------------管理员登陆-------------------
 */

    //系统首页
    @ApiOperation("系统首页")
    @GetMapping({"/index","/","/index.html"})
    @WebLogIgnore
    public String home(Model model){
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (principal instanceof User){
            model.addAttribute("username",((User) principal).getUserName());
        }else {
            model.addAttribute("username",null);
        }
        return "index";
    }

    //打开系统登录
    @ApiOperation("打开系统登录页面")
    @GetMapping("/login")
    @WebLogIgnore
    public String login(){
        return "login";
    }

    //系统登录
    @UserLogin
    @ApiOperation("系统登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "登录用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "用户密码",required = true,dataType = "String")
    })
    @PostMapping("/login")
    public String login(@RequestParam("username") String name, @RequestParam("password")String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        try {
            subject.login(token);
            model.addAttribute("welcomeMsg",name);
            return "redirect:/";
        } catch (UnknownAccountException uae){
            model.addAttribute("errorMsg","用户未注册，请先联系管理员完成注册！");
            return "login";
        } catch (IncorrectCredentialsException ice){
            model.addAttribute("errorMsg","账号密码错误");
            return "login";
        }
    }


    //未授权
    @ApiOperation("未授权")
    @GetMapping("/unauth")
    @WebLogIgnore
    public String unauthorized(){
        return "unauthorized";
    }

    //系统欢迎页
    @ApiOperation("欢迎进入系统")
    @GetMapping("/commons/welc")
    @WebLogIgnore
    public String welcome(Model model){
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (principal instanceof User) {
            model.addAttribute("username",((User) principal).getUserName());
        }
        else model.addAttribute("username",null);
        return "commons/welc/welcome";
    }

}
