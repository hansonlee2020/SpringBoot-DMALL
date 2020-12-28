package com.hanson.aop;

import com.alibaba.fastjson.JSONObject;
import com.hanson.annotation.UserLogin;
import com.hanson.annotation.WebLogIgnore;
import com.hanson.common.constant.Constant;
import com.hanson.common.handler.LogMessage;
import com.hanson.pojo.vo.User;
import com.hanson.pojo.vo.WebLog;
import com.hanson.service.WebLogService;
import com.hanson.utils.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @CLassName WebLogAspect
 * @Description 请求日志记录处理
 * @Author li hao xin
 * @Date 2020/11/26 14:41
 **/
@Slf4j
@Aspect
public class WebLogAspect {

    @Autowired
    private WebLogService webLogService;
    @Autowired
    private BaiduUtil baiduUtil;
    @Autowired
    private RedisUtil redisUtil;

    private static ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<>("beginTimeThreadLocal");
    private static final String  USER_IP = "USER_IP:";
    private static final String SEPARATOR = ":";

    @Pointcut("execution(* com.hanson.controller.*Controller.*(..))")
    public void webLogPointcut(){};

    @Pointcut("execution(* com.hanson.service.impl.*.*(..))")
    public void exceptionPointcut(){}

    @Before("webLogPointcut()")
    public void requestWebLog(JoinPoint joinPoint){
        //获取当前配置环境
        String activeProfile = SpringContextUtil.getActiveProfile();
        //非生产环境打印日志
        if (!Constant.ENVIROMENT_PRO.equals(activeProfile)){
            HttpServletRequest request = getHttpServletRequest();
            //接收请求日志
            log.info("【RECORD HTTP REQUEST LOG】");
            //记录请求内容
            log.info("URL=[{}]\t[{}]",  ((MethodSignature)joinPoint.getSignature()).getMethod(), request.getRequestURL().toString());
            log.info("IP=[{}]", IpUtils.getIp());
            log.info("CLASS_METHOD=[{}.{}]", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            //向下转型
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取请求参数
            Object[] args = joinPoint.getArgs();
            if (Constant.ENVIROMENT_DEV.equals(activeProfile) || Constant.ENVIROMENT_TEST.equals(activeProfile)){
                try {
                    Class<?> clazz = signature.getDeclaringType();
                    Method method = clazz.getMethod(signature.getName(), signature.getParameterTypes());
                    Annotation[] annotations = method.getAnnotations();
                    //获取目标注解，如果是用户登录注解则对请求参数里的密码进行隐藏
                    Annotation targetAnnotation = null;
                    for (Annotation annotation : annotations) {
                        if ((UserLogin.class).equals(annotation.annotationType())){
                            targetAnnotation = annotation;
                        }
                    }
                    if (targetAnnotation != null){
                        args[1] = args[1].toString().replaceAll(args[1].toString(),"****************");
                    }
                } catch (NoSuchMethodException e) {
                    log.error("反射获取方法异常,methodName = [{}]",signature.getName(),e);
                }
            }
            log.info("ARGS=[{}]", Arrays.toString(args));
        }
        /*
         **********************测试log*******************
         */

        /*
         **********************测试log*******************
         */
        //开始时间
        Date begin = new Date();
        beginTimeThreadLocal.set(begin);

    }

    @AfterReturning(pointcut = "webLogPointcut()",returning = "result")
    @Order(10)
    public void responseWebLog(JoinPoint joinPoint,Object result) {
        String activeProfile = SpringContextUtil.getActiveProfile();
        String user = getCurrentUser();
        if (!Constant.ENVIROMENT_PRO.equals(activeProfile)){
            // 处理完请求，返回结果日志
            log.info("【RECORD HTTP RESPONSE LOG】");
            log.info("CLASS_METHOD=[{}.{}]", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            log.info("RESPONSE=[{}]", result.toString());
        }
        /*
         **********************测试log*******************
         */

        /*
         **********************测试log*******************
         */
        HttpServletRequest request = getHttpServletRequest();
        //记录接口请求日志,忽略页面不记录
        if (!((MethodSignature)joinPoint.getSignature()).getMethod().isAnnotationPresent(WebLogIgnore.class)){
            WebLog webLog = new WebLog();
            webLog.setCreateBy(user);
            webLog.setResponseResult(result);
            //设置耗时
            setConsumingTime(webLog);
            //异步提交任务创建日志
            SyncCreateWebLogThread syncCreateWebLogThread = new SyncCreateWebLogThread(webLog,request,user,joinPoint);
            syncTaskExecute(syncCreateWebLogThread);
        }
    }

    @AfterThrowing(pointcut = "exceptionPointcut()",throwing = "exception")
    @Order(10)
    public void exceptionHandle(JoinPoint joinPoint, Throwable exception){
        LogMessage logMessage = new LogMessage();
        logMessage.setTarget(joinPoint.getSignature().toString());
        logMessage.setArgs(Arrays.toString(joinPoint.getArgs()));
        logMessage.setKind(joinPoint.getKind());
        logMessage.setError(exception.getMessage());
        String msg = JSONObject.toJSONString(logMessage, true);
        //记录到log4j2日志
        log.error(msg,exception);
        //记录请求异常日志
        String user = getCurrentUser();
        WebLog webLog = new WebLog();
        webLog.setCreateBy(user);
        webLog.setException(msg);
        setConsumingTime(webLog);
        SyncCreateWebLogThread syncCreateWebLogThread = new SyncCreateWebLogThread(webLog,getHttpServletRequest(),user,joinPoint);
        syncTaskExecute(syncCreateWebLogThread);
    }

    /**
    *
    * 获取HttpServletRequest对象
    *
    * @params: []
    * @param:
    * @return: {@link HttpServletRequest}
    * @since: 2020/12/9
    **/
    public HttpServletRequest getHttpServletRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }

    /**
     * 内部类：异步日志记录线程
     */
    private class SyncCreateWebLogThread implements Runnable{
        private WebLog webLog;
        private String user;
        private HttpServletRequest request;
        private JoinPoint joinPoint;
        public SyncCreateWebLogThread(WebLog webLog,HttpServletRequest request,String user,JoinPoint joinPoint){
            this.webLog = webLog;
            this.user = user;
            this.request = request;
            this.joinPoint = joinPoint;
        }

        @Override
        public void run() {
            //设置日志对象
            createWebLogObject(webLog,request,user,joinPoint);
            webLogService.createWebLog(webLog);
        }
    }

    /**
    *
    * 设置日志对象
    *
    * @params: [webLog, request, joinPoint]
    * @param: webLog		日志对象
    * @param: request		请求
    * @param user           当前用户
    * @param: joinPoint     连接点
    * @since: 2020/12/9
    **/
    private void createWebLogObject(WebLog webLog,HttpServletRequest request,String user,JoinPoint joinPoint){
        String logName;
        if (joinPoint.getSignature().getDeclaringType().getName().matches("\\*Controller*")){
            logName = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(ApiOperation.class).value();
        }else {
            logName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        }
        //设置日志名称
        webLog.setLogName(logName);
        //设置请求参数
        webLog.setMapToParams(request.getParameterMap());
        String requestURI = request.getRequestURI();
        if (StringUtils.isBlank(requestURI)){
            requestURI = "/";
        }
        //设置请求路径
        webLog.setUrl(requestURI);
        //设置ip
        String ipAddr = IpUtils.getIpAddr(request);
        webLog.setIp(ipAddr);
        log.info("ip------------->" + ipAddr);
        /*
        *********这里导致一个问题，分页数据时，返回正常结果，即code=200，data=[],但是数据为空*******
         */
        //去redis缓存去取当前用户的ip和当前的ip比对是否为同一ip
        try {
            String userIp = redisUtil.get(USER_IP + user + SEPARATOR + ipAddr);
            if (StringUtils.isNotBlank(userIp)){
                //缓存可以查询到用户的ip解析记录
                webLog.setIpAddress(userIp);
            }else {
                //去ip服务器进行解析，然后保存到缓存中
                //只能一个线程去请求，因为qps限制
                synchronized (this){
                    //再次确定redis是否有缓存数据
                    String ensureUserIp = redisUtil.get(USER_IP + user + SEPARATOR + ipAddr);
                    if (StringUtils.isBlank(ensureUserIp)){
                        log.info("去请求ip地址服务器----->ip="+ipAddr);
                        String ipString = questIp(ipAddr);
                        if (StringUtils.isNotBlank(ipString)){
                            redisUtil.setEx(USER_IP + user + SEPARATOR + ipAddr,ipString,30, TimeUnit.MINUTES);
                        }else {
                            ipString = "QUEST_ERROR";
                        }
                        webLog.setIpAddress(ipString);
                    }else {
                        //走缓存
                        //设置ip解析地址
                        webLog.setIpAddress(ensureUserIp);
                    }
                }
            }
        } catch (Exception e) {
            log.error("设置ip解析地址异常，ip=[{}]",ipAddr,e);
        }
        /*
         *********这里导致一个问题，分页数据时，返回正常结果，即code=200，data=[],但是数据为空*******
         */
    }

    /**
    *
    * 设置耗时
    *
    * @params: [webLog]
    * @param: webLog    日志对象
    * @since: 2020/12/9
    **/
    private void setConsumingTime(WebLog webLog){
        //取出请求开始时间
        long begin = beginTimeThreadLocal.get().getTime();
        long end = System.currentTimeMillis();
        webLog.setTimeConsuming(end - begin);
        //remove the Date Object form threadLocal
        beginTimeThreadLocal.remove();
    }

    /**
    *
    * 异步提交任务给线程池
    *
    * @params: [runnable]
    * @param: runnable
    * @since: 2020/12/9
    **/
    private void syncTaskExecute(Runnable runnable){
        ThreadPoolUtil.getPool().execute(runnable);
    }

    /**
    *
    * 获取当前用户的用户名
    *
    * @params: []
    * @param:
    * @return: {@link String}
    * @since: 2020/12/14
    **/
    private String getCurrentUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String resultUser = null;
        if (principal != null){
            if (principal instanceof User){
                resultUser = ((User)principal).getUserName();
            }else {
                resultUser = "未知用户";
            }
        }
        //不需要登录就能访问的接口的请求，没有user
        if (resultUser == null){
            resultUser = "DMALL系统";
        }
        return resultUser;
    }

    /**
    *
    * 请求ip解析
    *
    * @params: [ipAddr] 需解析的ip地址
    * @param: ipAddr
    * @return: {@link String}   ip地址
    * @since: 2020/12/28
    **/
    private synchronized String questIp(String ipAddr){
        return IpUtils.getIpString(baiduUtil.getIpAddress(ipAddr));
    }

}
