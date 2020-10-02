package com.hanson.config;

import com.alibaba.fastjson.JSONObject;
import com.hanson.common.api.CommonResult;
import com.hanson.common.api.ResultCode;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: manager
 * @description: 请求拦截识别分派过滤器
 * 拦截页面所有的ajax请求，主要用于拦截未授权的用户的敏感操作，例如拦截test用户删除数据，
 * 修改数据等操作，未授权返回指定json消息
 * @param:
 * @author: Hanson
 * @create: 2020-09-08 13:45
 **/
public class PermissionFilter extends PermissionsAuthorizationFilter {
    /* 
    * @description: 回调函数，拦截ajax请求后，进行授权，授权失败调用该函数返回未授权json消息 
    * @params: [request, response] 
    * @return: boolean 
    * @throws: Exception
    * @Date: 2020/9/8 
    */ 
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestedWith = httpServletRequest.getHeader("X-Requested-With");
        if (StringUtils.isNotEmpty(requestedWith) &&
                StringUtils.equalsIgnoreCase(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定格式数据
            CommonResult<Object> result = new CommonResult<>(ResultCode.FORBIDDEN,null);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(result));
        } else {
            //如果是普通请求进行重定向
            httpServletResponse.sendRedirect("/unauth");
        }
        return false;
    }
}
