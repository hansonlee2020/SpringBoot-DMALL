package com.hanson.service;

/**
 * @program: manager
 * @description: 系统配置服务
 * @param:
 * @author: Hanson
 * @create: 2020-09-07 16:17
 **/
public interface SystemService {
    String getSysHost();
    String getSystemEdition();
    String getCore();
    String getSystemTime();
    String getJvmMemory();
    String getJvmFreeMemory();
}
