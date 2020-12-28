package com.hanson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: ip地址解析服务器地址配置
 * @classname: IpProperties
 * @author: Hanson
 * @create: 2020/12/11
 **/
@Component
@ConfigurationProperties(prefix = "ip.address",ignoreUnknownFields = false)
public class IpProperties {

    //ip地址解析服务器地址
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
