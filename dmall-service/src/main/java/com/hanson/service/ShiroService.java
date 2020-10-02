package com.hanson.service;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.Map;

/**
 * @program: manager
 * @description: Shiro动态配置拦截链接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-26 13:47
 **/
public interface ShiroService {

    /* 
    * @description: 从数据库动态加载shiro拦截配置链 
    * @params: [] 
    * @return: java.util.Map<java.lang.String,java.lang.String> 
    * @throws: Exception
    * @Date: 2020/9/26 
    */ 
    public Map<String,String> loadFilterChainDefinitions();
    
    
    
    /* 
    * @description: 对当前角色进行增删改是动态刷新权限 
    * @params: [shiroFilterFactoryBean] 
    * @return: void 
    * @throws: Exception
    * @Date: 2020/9/26 
    */ 
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean);
}
