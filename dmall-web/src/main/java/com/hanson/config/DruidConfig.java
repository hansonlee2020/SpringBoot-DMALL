package com.hanson.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: druid数据源配置
 * @description:
 * @param:
 * @author: Hanson
 * @create: 2020-08-20 11:39
 **/

@Configuration
public class DruidConfig {

    @Value("${spring.datasource.loginUsername}")
    private String loginUsername;

    @Value("${spring.datasource.loginPassword}")
    private String loginPassword;


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")//绑定properties
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //springboot 内置servlet容器，没有web.xml，所以替代方法时ServletRegistrationBean
    @Bean
    public ServletRegistrationBean StatViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        //后台登陆，配置用户密码
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("loginUsername",loginUsername);//登陆的key是固定的
        initParameters.put("loginPassword",loginPassword);
        //允许谁访问
        initParameters.put("allow","");
        //禁止谁访问     initParameters.put("hanson","192.168.11.123");
        bean.setInitParameters(initParameters);//初始化参数
        return bean;
    }
    //还可以配置filter
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("exclusions", "/css/**,/js/**,/lib/**,/images/**,/fonts/**,*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters(initParameters);
        // 验证所有请求
        bean.addUrlPatterns("/*");
        return bean;
    }

    /*
     * 配置事物管理器
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }
    /*
     * ↓↓↓↓↓↓  配置spring监控  ↓↓↓↓↓↓
     * DruidStatInterceptor: druid提供的拦截器
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    /*
     * 使用正则表达式配置切点
     */
    @Bean
    @Scope("prototype")
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("com.hanson.service.impl.*");
        return pointcut;
    }
    /*
     * DefaultPointcutAdvisor类定义advice及 pointcut 属性。advice指定使用的通知方式，也就是druid提供的DruidStatInterceptor类，pointcut指定切入点
     */
    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }

}
