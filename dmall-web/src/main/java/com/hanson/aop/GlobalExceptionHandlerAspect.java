package com.hanson.aop;

import com.alibaba.fastjson.JSONObject;
import com.hanson.common.handler.LogMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @program: manager
 * @description: 全局异常日志记录
 * @param:
 * @author: Hanson
 * @create: 2020-10-02 14:34
 **/
@Aspect
@Component
public class GlobalExceptionHandlerAspect {

    private final static Logger logger = LoggerFactory.getLogger(shiroAspect.class);

    @Pointcut("execution(* com.hanson.service.impl.*.*(..))")
    public void exceptionPointcut(){}

    @AfterThrowing(pointcut = "exceptionPointcut()",throwing = "exception")
    public void ExceptionHandle(JoinPoint joinPoint,Throwable exception){
        LogMessage logMessage = new LogMessage();
        logMessage.setTarget(joinPoint.getSignature().toString());
        logMessage.setArgs(Arrays.toString(joinPoint.getArgs()));
        logMessage.setKind(joinPoint.getKind());
        logMessage.setError(exception.getMessage());
        String msg = JSONObject.toJSONString(logMessage, true);
        logger.error(msg,exception);
    }
}
