package com.hanson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 百度配置绑定
 * @classname: BaiduProperties
 * @author: Hanson
 * @create: 2020/12/11
 **/
@Component
@ConfigurationProperties(prefix = "baidu",ignoreUnknownFields = false)
public class BaiduProperties {
    /*
    百度app密钥
     */
    private String appSecret;
    /*
    百度公钥
     */
    private String accesskey;

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }
}
