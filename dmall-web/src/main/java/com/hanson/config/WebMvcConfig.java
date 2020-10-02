package com.hanson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @program: manager
 * @description: mvc过滤器拦截配置
 * @param:
 * @author: Hanson
 * @create: 2020-09-08 12:58
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /*
    * 配置静态资源文件路径
    * @params: [registry]
    * @return: void
    * @throws: Exception
    * @Date: 2020/9/8
    */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**","/fonts/**","/lib/**","/js/**","/images/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/css/",
                        ResourceUtils.CLASSPATH_URL_PREFIX + "/fonts/",
                        ResourceUtils.CLASSPATH_URL_PREFIX + "/lib/",
                        ResourceUtils.CLASSPATH_URL_PREFIX + "/js/",
                        ResourceUtils.CLASSPATH_URL_PREFIX + "/images/");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }
}
