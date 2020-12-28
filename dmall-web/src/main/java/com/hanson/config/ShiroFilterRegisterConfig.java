package com.hanson.config;

 import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: manager
 * @description: shiro过滤器注册配置，主要解决SpringBoot自动注册管理这些过滤器，这是我不希望的
 * @param:
 * @author: Hanson
 * @create: 2020-09-08 15:24
 **/
@Configuration
public class ShiroFilterRegisterConfig {
    //禁止将登出过滤器交由SpringBoot管理
    @Bean
    public FilterRegistrationBean<DMLogoutFilter> shiroLogOutFilterRegistration(DMLogoutFilter logoutFilter) {
        FilterRegistrationBean<DMLogoutFilter> registrationBean = new FilterRegistrationBean<>(logoutFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
    //禁止将请求过滤器交由SpringBoot管理
    @Bean
    public FilterRegistrationBean<PermissionFilter> shiroPermissionFilter(PermissionFilter permissionFilter){
        FilterRegistrationBean<PermissionFilter> registrationBean = new FilterRegistrationBean<>(permissionFilter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}
