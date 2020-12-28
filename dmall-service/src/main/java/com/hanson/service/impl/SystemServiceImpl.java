package com.hanson.service.impl;

import com.hanson.service.SystemService;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @program: manager
 * @description: 获取系统信息
 * @param:
 * @author: Hanson
 * @create: 2020-09-07 17:35
 **/
@Service
public class SystemServiceImpl implements SystemService {

    private final static long BYTE_TO_MB = 1024*1024;

    @Override
    public String getSysHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getSystemEdition() {
        Properties properties = System.getProperties();
        String name = properties.getProperty("os.name");
        Object version = properties.get("os.version");
        return name + " " + version;
    }

    @Override
    public String getCore() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public String getSystemTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }

    @Override
    public String getJvmMemory() {
        return String.valueOf(Runtime.getRuntime().totalMemory()/BYTE_TO_MB);
    }

    @Override
    public String getJvmFreeMemory() {
        return String.valueOf(Runtime.getRuntime().freeMemory()/BYTE_TO_MB);
    }
}
