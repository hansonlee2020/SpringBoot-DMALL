package com.hanson.config;

import cn.hutool.core.date.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @program: manager
 * @description: Swagger配置
 * @param:
 * @author: Hanson
 * @create: 2020-09-29 14:47
 **/
@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private final Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";


    /*
     * Swagger Springfox configuration.
     */
    @Bean
    public Docket swaggerSpringfoxDocket(SwaggerProperties properties, Environment environment) {
        LOG.debug("Starting Swagger");
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev","test","pro");
        //通过environment.acceptsProfiles(profiles);判断是否处于自己设定的环境下
        boolean flag = environment.acceptsProfiles(profiles);
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = new Contact(
                properties.getSwagger().getContactName(),
                properties.getSwagger().getContactUrl(),
                properties.getSwagger().getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
                properties.getSwagger().getTitle(),
                properties.getSwagger().getDescription(),
                properties.getSwagger().getVersion(),
                properties.getSwagger().getTermsOfServiceUrl(),
                contact,
                properties.getSwagger().getLicense(),
                properties.getSwagger().getLicenseUrl(),
                new ArrayList<>());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("HansonLee")
                .enable(flag)
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hanson.controller"))
                .paths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();

        watch.stop();
        LOG.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }
}

