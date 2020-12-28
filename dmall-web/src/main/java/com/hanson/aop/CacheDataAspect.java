package com.hanson.aop;

import com.alibaba.fastjson.JSONObject;
import com.hanson.annotation.CacheClear;
import com.hanson.annotation.CacheData;
import com.hanson.common.constant.Constant;
import com.hanson.utils.RedisUtil;
import com.hanson.utils.SpringContextUtil;
import com.hanson.utils.ThreadPoolUtil;
import com.hanson.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @description: 设置或取出缓存数据，@CacheData注解处理切面
 * @classname: CacheDataAspect
 * @author: Hanson
 * @create: 2020/12/14
 **/
@Slf4j
@Component
@Aspect
public class CacheDataAspect {
    @Autowired
    private RedisUtil redisUtil;

    @Pointcut(value = "@annotation(com.hanson.annotation.CacheData))")
    public void cacheDataPointcut(){}

    @Pointcut(value = "@annotation(com.hanson.annotation.CacheClear))")
    public void cacheClearPointcut(){}

    @Around(value = "cacheDataPointcut()")
    public Object cacheDataHandler(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //是否使用缓存
        if (method.isAnnotationPresent(CacheData.class)){
            CacheData annotation = method.getAnnotation(CacheData.class);
            String cacheKey = annotation.cacheKey();
            Class<?> dataClazz = annotation.dataClass();
            long expireTime = annotation.expireTime();
            TimeUnit timeUnit = annotation.timeUnit();
            //检查是否是获取列表第一页内容
            HttpServletRequest request = WebUtil.getHttpServletRequest();
            String page = "2";
//            String page = request.getParameter("page");
//            String limit = request.getParameter("limit");
//            log.info("method----->" + method.getName() + ",page----->" + page);
//            log.info("method----->" + method.getName() + ",limit----->" + limit);
            if ("1".equals(page)){
                //第一页，去缓存取数据
                String resultJson = redisUtil.get(cacheKey);
                //缓存有数据，直接返回
                if (StringUtils.isNotBlank(resultJson)){
                    return JSONObject.parseObject(resultJson, dataClazz);
                }
                //缓存没有数据，继续执行
            }
            Object result = "";
            try {
                result = joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("CacheDataAspect切面(使用缓存)执行异常",throwable);
            }
            //将第一页数据放进缓存
            if ("1".equals(page)){
                redisUtil.setEx(cacheKey,(JSONObject.toJSONString(result)),expireTime,timeUnit);
            }
            //返回结果
            return result;
        }
        //不使用缓存，继续执行
        Object result = "";
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("CacheDataAspect切面(非使用缓存)执行异常",throwable);
        }
        return result;
    }

    @AfterReturning(value = "cacheClearPointcut()",returning = "result")
    public void cacheClearHandle(JoinPoint joinPoint,Object result){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(CacheClear.class)){
            CacheClear cacheClear = method.getAnnotation(CacheClear.class);
            String[] keys = cacheClear.keys();
            //创建异步线程清理缓存
            createCacheClearThread(keys);
        }
    }

    /**
    *
    * 创建缓存清理线程
    *
    * @params: [keys]
    * @param: keys  需要清理的keys
    * @since: 2020/12/14
    **/
    private void createCacheClearThread(String[] keys){
        SyncCacheClearThread syncCacheClearThread = new SyncCacheClearThread(keys);
        ThreadPoolUtil.getPool().execute(syncCacheClearThread);
    }

    /*
      缓存清理进程
     */
    private class SyncCacheClearThread implements Runnable{
        private String[] keys;

        public SyncCacheClearThread(String[] keys){
            this.keys = keys;
        }

        @Override
        public void run() {
            for (String key : keys) {
                redisUtil.delete(key);
                String activeProfile = SpringContextUtil.getActiveProfile();
                if (!(Constant.ENVIROMENT_PRO.equals(activeProfile))){
                    log.info("缓存已清理" + key);
                }
            }
        }
    }
}
