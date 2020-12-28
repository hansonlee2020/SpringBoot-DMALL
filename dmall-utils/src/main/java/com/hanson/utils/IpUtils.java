package com.hanson.utils;

import cn.hutool.core.bean.BeanUtil;
import com.hanson.common.pojo.City;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @CLassName IpUtils
 * @Description ip工具
 * @Author li hao xin
 * @Date 2020/11/26 14:28
 **/
public class IpUtils {
    private final static Logger log =LoggerFactory.getLogger(IpUtils.class);

    private final static String ANY_HOST = "0.0.0.0";
    private final static String LOCAL_HOST = "127.0.0.1";
    private final static String IPV6 = "0:0:0:0:0:0:0:1";
    private final static Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static volatile InetAddress LOCAL_ADDRESS= null;
    /**
     * Mob官网注册申请即可
     */
    private final static String APPKEY = "31b5df5f63d3c";
    /**
     * Mob全国天气预报接口
     */
    private final static String GET_WEATHER="http://apicloud.mob.com/v1/weather/ip?key="+ APPKEY +"&ip=";

    private IpUtils(){}

    /**
     * get ip
     * @return
     */
    public static String getIp(){
        InetAddress address = getAddress();
        if (address == null){
            return null;
        }
        return address.getHostAddress();
    }

    /**
     * get address
     * @return
     */
    private static InetAddress getAddress() {
        if (LOCAL_ADDRESS != null){
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getFirstValidAddress();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }


    /**
     * get first valid address
     * @return
     */
    private static InetAddress getFirstValidAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            log.error("Failed to retrying ip address, " + e.getMessage(), e);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    log.error("Failed to retrying ip address, " + e.getMessage(), e);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        log.error("Failed to retrying ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            log.error("Failed to retrying ip address, " + e.getMessage(), e);
        }
        log.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    /**
     * valid address
     * @param address
     * @return
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null
                && ! ANY_HOST.equals(name)
                && ! LOCAL_HOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

    /**
     * 获取客户端IP地址
     * @param request 请求
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (LOCAL_HOST.equals(ip) || IPV6.equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        log.info("request.getRemoteAddr=" + ip);
        return ip;
    }


    /**
     * 获取IP返回地理信息
     * @param city 城市对象
     * @return
     */
    public static String getIpString(City city){
        if(null != city){
            if (!BeanUtil.isEmpty(city)){
                return (StringUtils.isNotBlank(city.getNation()) ?
                        city.getNation() : "")
                        + (StringUtils.isNotBlank(city.getRegion()) ?
                        " "  + city.getRegion() : "")
                        + (StringUtils.isNotBlank(city.getCity()) ?
                        " "  + city.getCity() : "");
            }
            return "-";
        }
        return "未知";
    }
}
