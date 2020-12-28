package com.hanson.utils;

import com.alibaba.fastjson.JSONObject;
import com.hanson.common.pojo.City;
import com.hanson.config.AppSigner;
import com.hanson.config.BaiduProperties;
import com.hanson.config.IpProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 百度工具
 * @classname: BaiduUtil
 * @author: Hanson
 * @create: 2020/12/11
 **/
@Component
public class BaiduUtil {
    private static Logger log = LoggerFactory.getLogger(BaiduUtil.class);

    @Autowired
    private BaiduProperties baiduProperties;

    @Autowired
    private IpProperties ipProperties;

    public City getIpAddress(String ip){
        String url = ipProperties.getUrl();
        AppSigner.Credential credential = new AppSigner.Credential(baiduProperties.getAccesskey(), baiduProperties.getAppSecret());
        AppSigner appSigner = new AppSigner(credential);

        String httpMethod = "GET";
        URI uri = null;
        Map<String, String> headers = new HashMap<>();
        try {
            uri = new URIBuilder(url).setParameter("ip",ip).build();
            headers.put("Host", uri.getHost());
        } catch (URISyntaxException e) {
            log.error("请求的URI异常，url=[{}]",url,e);
        }

        // Signature header will be added to headers.
        appSigner.sign(httpMethod, uri, headers);
        //获取签名后的所有请求头信息
        List<Header> headerList = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
            BasicHeader basicHeader = new BasicHeader(stringStringEntry.getKey(),stringStringEntry.getValue());
            headerList.add(basicHeader);
        }
        //发送get请求获取ip信息
        String decodeUnicode = "";
        try {
            long start = System.currentTimeMillis();
            decodeUnicode = HttpUtil.sendGet(uri, headerList.toArray(Header[]::new), true);
            if(StringUtils.isBlank(decodeUnicode)){
                throw new RuntimeException("返回结果数据为空！");
            }
            long cost = System.currentTimeMillis() - start;
            if (cost > 3000){
                throw new RuntimeException("请求ip地址解析服务器超时");
            }
            JSONObject jsonObject = JSONObject.parseObject(decodeUnicode);
            //设置id信息
            City city = new City();
            //解析json字符串获取ip解析数据
            boolean containsKey = jsonObject.containsKey("data");
            if (containsKey && StringUtils.isNotBlank(jsonObject.getString("data")) && !("[]".equals(jsonObject.getString("data")))){
                log.info("data--->" + jsonObject.getString("data"));
                JSONObject data = jsonObject.getJSONObject("data");
                if (data != null && data.containsKey("details") && StringUtils.isNotBlank(data.getString("details")) && !("[]".equals(data.getString("details")))){
                    log.info("details--->" + data.getString("details"));
                    JSONObject details = data.getJSONObject("details");
                    if (details != null){
                        city.setCity(details.getString("city"));
                        city.setRegion(details.getString("region"));
                    }
                    city.setNation(data.getString("nation"));
                }else {
                    city.setNation("默认IP地址");
                }
            }
            return city;
        } catch (Exception e) {
            log.error("ip地址服务器解析异常，服务器返回结果字符串，resultJson = [{}]",decodeUnicode,e);
        }
        return null;
    }
}
