package com.hanson.config;

import com.hanson.aop.WebLogAspect;
import com.hanson.common.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @description: 系统配置
 * @classname: SystemConfig
 * @author: Hanson
 * @create: 2020/12/18
 **/
@Configuration
public class SystemConfig {

    //生成环境下注册系统接口操作日志切面对象
    @Profile(Constant.ENVIROMENT_PRO)
    @Bean
    public WebLogAspect webLogAspect(){
        return new WebLogAspect();
    }
}
