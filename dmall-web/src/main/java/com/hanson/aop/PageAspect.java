package com.hanson.aop;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

/**
 * @CLassName PageAspect
 * @Description 分页切面
 * @Author hanson
 * @Date 2020/12/1 10:33
 **/
//@Aspect
@Slf4j
//@Component
public class PageAspect {

    @Pointcut(value = "execution(* com.hanson.controller.*Controller.*ListPage(..))" + "&& args(request)",argNames = "request")
    public void pageControllerPointCut(HttpServletRequest request){}

    @Pointcut("execution(* com.hanson.service.*Service.*Split(..))")
    public void pageServicePointCut(){}

    @Before(value = "pageControllerPointCut(request)", argNames = "joinPoint,request")
    public void pageBefore(JoinPoint joinPoint ,HttpServletRequest request){
        log.info("↓↓↓↓↓↓分页前置处理，设置分页属性开始↓↓↓↓↓↓");
        String limit = request.getParameter("limit");
        String page = request.getParameter("page");
        log.info("limit------------->" + limit);
        log.info("page------------->" + page);
        if (StringUtils.isNotBlank(limit) && StringUtils.isNotBlank(page)){
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            Parameter[] parameters = signature.getMethod().getParameters();
            if (!ArrayUtil.isEmpty(parameters)){
                for (Parameter parameter : parameters) {
                    Class<?> type = parameter.getType();
                    if (type.equals(com.hanson.utils.PageUtil.class)){
                        if (parameter.isAnnotationPresent(com.hanson.common.annotation.PageUtil.class)){
                            Class<?> parameterType = parameter.getType();
                            try {
                                Constructor<?> parameterTypeDeclaredConstructor = parameterType.getDeclaredConstructor(int.class, int.class);
                                Object pageUtil = parameterTypeDeclaredConstructor.newInstance(Integer.valueOf(page), Integer.valueOf(limit));
                                request.setAttribute("pageUtil",pageUtil);
                                log.info("pageUtil------------>" + request.getParameter("pageUtil"));
                            } catch (NoSuchMethodException e) {
                                log.error("反射获取构造器异常，classType = [{}],constructorParameterType[] = [{}]",parameterType,new Object[]{int.class,int.class},e);
                            } catch (IllegalAccessException e) {
                                log.error("反射非法访问异常",e);
                            } catch (InstantiationException e) {
                                log.error("反射实例化异常",e);
                            } catch (InvocationTargetException e) {
                                log.error("反射调用异常",e);
                            }
                        }
                    }
                }
            }
        }
        log.info("↑↑↑↑↑↑分页前置处理，设置分页属性结束↑↑↑↑↑↑");
    }

    @AfterReturning(pointcut = "pageServicePointCut()",returning = "result")
    public void pageAfter(JoinPoint joinPoint,Object result){
        log.info("↓↓↓↓↓↓分页结果后置处理，设置分页结果开始↓↓↓↓↓↓");
        //run
        log.info("↑↑↑↑↑↑分页结果后置处理，设置分页结果结束↑↑↑↑↑↑");
    }
}
