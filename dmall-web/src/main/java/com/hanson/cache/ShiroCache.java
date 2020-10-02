package com.hanson.cache;

import com.hanson.common.cache.ICache;
import com.hanson.pojo.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: manager
 * @description: shiro认证和授权缓存
 * @param:
 * @author: Hanson
 * @create: 2020-09-27 13:36
 **/
@Component
public class ShiroCache implements ICache<Map<Long,Object>, Set<String>> {

    //权限信息缓存<过期时间,<用户id，权限>>
    protected final static Map<Map<Long,Object>,Set<String>> AUTH_CACHE = new ConcurrentHashMap<>();
    //缓存过期时间,默认为30分钟
    private final static long EXPIRE_TIME = 1000 * 60 * 30;
    //缓存清理延时，默认30秒
    private final static long LAZY_TIME = 1000 * 30;
    //缓存清理间隔，默认5分钟
    private final static long INTERVAL = 60 * 5;


    @Override
    public boolean addCache(Object target, Long expireTime, Set<String> value) {
        if (expireTime < 0) return false;
        clearCacheIfExpire();//每次添加缓存时，清理过期的缓存
        long current = System.currentTimeMillis();
        Map<Long,Object> map = new HashMap<>();
        map.put((expireTime + current),target);
        AUTH_CACHE.put(map,value);
//        System.out.println("添加缓存完毕，缓存目标大小--->" + AUTH_CACHE.size());
        return true;
    }

    @Override
    public Set<Map<Long, Object>> getKeys() {
        return AUTH_CACHE.keySet();
    }

    @Override
    public Set<String> getValues(Object target) {
        if (AUTH_CACHE.size() <= 0 | target == null) return null;
        Set<String> set = null;
        for (Map<Long, Object> map : AUTH_CACHE.keySet()) {
            for (Object o : map.values()) {
                if (target.equals(o)) {
                    set = AUTH_CACHE.get(map);
//                    System.out.println("#####缓存数据输出结束#####");
                }
            }
        }
        return set;
    }

    @Override
    public boolean isCacheEmpty() {
        return getKeys().size() <=0;
    }

    @Override
    public void clearCacheIfExpire() {
        long currentTime = System.currentTimeMillis();
        Set<Map<Long, Object>> maps = AUTH_CACHE.keySet();
        for (Map<Long, Object> map : maps) {
            for (Long next : map.keySet()) {
                if (next < currentTime) {
                    /*测试信息*/
//                    Collection<String> values = AUTH_CACHE.get(map);
//                    for (String value : values) {
//                        System.out.println("清理了缓存-->" + value);
//                    }
                    /*测试信息*/
                    AUTH_CACHE.remove(map);
                }
            }
        }
        //日志记录
    }

    @Override
    public boolean clearAll() {
        if (AUTH_CACHE.size() <= 0) return false;
        for (Map<Long,Object> key : AUTH_CACHE.keySet()) {
            AUTH_CACHE.remove(key);
        }
        return true;
    }

    @Override
    public void autoCacheClear(long expireTime, long lazyTime,
                               TimeUnit timeUnit, long interval,
                               String threadName) {
        if (expireTime <= 0) {
            expireTime = EXPIRE_TIME;
        }
        if (lazyTime <= 0){
            lazyTime = LAZY_TIME;
        }
        if (interval <= 0){
            interval = INTERVAL;
        }
        final long newTimeout = timeUnit.toMillis(expireTime);//过期时间
        final long newLazyTime = timeUnit.toMillis(lazyTime);//延时
        if (threadName == null | "".equals(threadName)) threadName = "CacheClear_Thread";
        //设置清理缓存的线程信息
        long finalInterval = interval;
        Thread thread = new Thread(()->{
            Subject current = SecurityUtils.getSubject();
            //登陆成功，设置session信息
            User user = (User) current.getPrincipal();
            //设置session过期时间
            current.getSession().setTimeout(newTimeout);
            //系统当前时间
            long currentTime = System.currentTimeMillis();
            //系统默认的过期时间
            long timeout = current.getSession().getTimeout();
            /*System.out.println(timeout);
            System.out.println("session过期时间："
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format((currentTime + timeout)));*/
            //用户已经登录或者已经认证了，绑定session信息
            if (user != null && current.isAuthenticated()) current.getSession().setAttribute(current.getSession().getId(),user.getUserName());
            //开启线程，清理用户缓存
            try {

                //获取session信息，如果抛出异常，说明session绑定的信息失效
                Object attribute = current.getSession().getAttribute(current.getSession().getId());
                //session过期或者超出过期时间表示缓存不用清理了，停止线程，一般设置一点点延时，保证缓存清理干净
                while ( attribute != null && System.currentTimeMillis() < (currentTime + timeout + newLazyTime)){
                    TimeUnit.SECONDS.sleep(finalInterval);
                    System.out.println("sessionID："+ current.getSession().getId() + "...缓存清理线程执行中......");
                    clearCacheIfExpire();
                    //更新session信息
                    attribute = current.getSession().getAttribute(current.getSession().getId());
                }
                System.out.println("清理缓存线程结束");
            } catch (Exception e) {
//                e.printStackTrace();
                //session失效，线程因为IllegalStateException异常终止
                System.out.println(e.getMessage());
            }

        },threadName);
        //开启线程
        thread.start();
    }
}
