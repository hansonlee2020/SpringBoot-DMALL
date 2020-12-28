package com.hanson.aop;

import com.hanson.service.ShiroService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: manager
 * @description: shiro配置aop，用于权限更改时重新配置拦截链
 * @param:
 * @author: Hanson
 * @create: 2020-09-28 18:23
 **/
@Aspect
@Component
public class shiroAspect {

    private final static Logger logger = LoggerFactory.getLogger(shiroAspect.class);

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Pointcut("execution(* com.hanson.service.impl.AuthorityServiceImpl.createAuthority(..)) || execution(* com.hanson.service.impl.AuthorityServiceImpl.updateAuthority(..)) || execution(* com.hanson..service.impl.AuthorityServiceImpl.deleteAuthority(..))")
    public void shiroPointcut(){}

    @AfterReturning("shiroPointcut()")
    public void reloadShiroConfig(){
        //重新加载权限配置
        shiroService.updatePermission(shiroFilterFactoryBean);
    }

}
