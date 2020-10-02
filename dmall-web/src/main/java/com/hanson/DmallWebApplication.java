package com.hanson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableTransactionManagement
@EnableCaching
@SpringBootApplication()
public class DmallWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmallWebApplication.class, args);
    }

}
