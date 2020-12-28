package com.hanson.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: manager
 * @description: shiro会话监听
 * @param:
 * @author: Hanson
 * @create: 2020-09-29 11:00
 **/
public class ShiroSessionListener implements SessionListener {

    //活跃会话数
    private final AtomicInteger sessionCount = new AtomicInteger();

    /* 
    * @description: 会话创建时触发 
    * @params: [session] 
    * @return: void 
    * @throws: Exception
    * @Date: 2020/9/29 
    */ 
    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    /* 
    * @description: 退出会话时触发 
    * @params: [session] 
    * @return: void 
    * @throws: Exception
    * @Date: 2020/9/29 
    */ 
    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    /* 
    * @description: 会话过期时触发
    * @params: [session] 
    * @return: void 
    * @throws: Exception
    * @Date: 2020/9/29 
    */ 
    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }

    /*
    * @description: 获取活跃session
    * @params: []
    * @return: java.util.concurrent.atomic.AtomicInteger
    * @throws: Exception
    * @Date: 2020/9/29
    */
    public AtomicInteger getSessionCount(){
        return sessionCount;
    }
}
