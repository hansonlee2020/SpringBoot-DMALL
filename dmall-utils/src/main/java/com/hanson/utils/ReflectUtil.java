package com.hanson.utils;

import cn.hutool.core.collection.CollectionUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description: 反射工具类
 * @classname: ReflectUtil
 * @author: Hanson
 * @create: 2020/12/14
 **/
public class ReflectUtil {

    /**
    *
    * 根据参数名称获取请求参数的值
    *
    * @params: [parameterName]
    * @param: parameterName
    * @return: {@link Object}
    * @since: 2020/12/14
    **/
    public static Object getParameterValueByName(String parameterName){
        HttpServletRequest request = WebUtil.getHttpServletRequest();
        return request.getParameter(parameterName);
    }

    /**
    *
    * 获取方法指定参数的指定注解
    *
    * @params: [method, parameterName, annotationName]
    * @param: method			方法对象
    * @param: parameterName     参数名
    * @param: annotationName    注解名
    * @return: {@link Class<?>}
    * @since: 2020/12/14
    **/
    public static Class<?> getParameterAnnotation(Method method,String parameterName,String annotationName){
        Set<Class<?>> parameterAnnotations = getParameterAnnotations(method, parameterName);
        if (!CollectionUtil.isEmpty(parameterAnnotations)){
            for (Class<?> parameterAnnotation : parameterAnnotations) {
                if (annotationName.equals(parameterAnnotation.getName())){
                    return parameterAnnotation;
                }
            }
        }
        return null;
    }

    /**
    *
    * 获取方法的指定参数名的所有注解
    *
    * @params: [method, parameterName]
    * @param: method		方法
    * @param: parameterName 参数名
    * @return: {@link java.util.Set<java.lang.Class<?>>} 键为参数名，值为参数的注解列表
    * @since: 2020/12/14
    **/
    public static Set<Class<?>> getParameterAnnotations(Method method, String parameterName){
        Map<String, Set<Class<?>>> parameterAnnotations = getParameterAnnotations(method);
        if (!CollectionUtil.isEmpty(parameterAnnotations)){
            Set<Class<?>> annotationsClazz = parameterAnnotations.get(parameterName);
            if (!CollectionUtil.isEmpty(annotationsClazz)){
                return annotationsClazz;
            }
        }
        return null;
    }

    /**
     *
     * 获取方法的参数的所有注解
     *
     * @params: [method]
     * @param: method		方法
     * @return: {@link Map< String, Set< Class<?>>>} 键为参数名，值为参数的注解class对象列表
     * @since: 2020/12/14
     **/
    public static Map<String, Set<Class<?>>> getParameterAnnotations(Method method){
        Map<String, Set<Class<?>>> annotationsMap = new HashMap<>(16);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            Annotation[] annotations = parameter.getAnnotations();
            Set<Class<?>> parameterAnnotationsSet = new HashSet<>();
            for (Annotation annotation : annotations) {
                parameterAnnotationsSet.add(annotation.getClass());
            }
            annotationsMap.put(parameter.getName(),parameterAnnotationsSet);
        }
        return annotationsMap;
    }

    /**
    *
    * 获取类的指定方法的指定注解
    *
    * @params: [clazz, methodName, annotationName]
    * @param: clazz				类class对象
    * @param: methodName		方法名
    * @param: annotationName    注解名
    * @return: {@link Class<?>}
    * @since: 2020/12/14
    **/
    public static Class<?> getMethodAnnotation(Class<?> clazz,String methodName,String annotationName){
        Set<Class<?>> methodAnnotations = getMethodAnnotations(clazz, methodName);
        if (CollectionUtil.isNotEmpty(methodAnnotations)){
            for (Class<?> methodAnnotation : methodAnnotations) {
                if (annotationName.equals(methodAnnotation.getName())){
                    return methodAnnotation;
                }
            }
        }
        return null;
    }

    /**
    *
    * 获取类的指定方法名的所有注解
    *
    * @params: [clazz, method, methodName]
    * @param: clazz				类class对象
    * @param: method			方法对象
    * @param: methodName        方法名称
    * @return: {@link Set< Class<?>>}
    * @since: 2020/12/14
    **/
    public static Set<Class<?>> getMethodAnnotations(Class<?> clazz,String methodName){
        Map<String, Set<Class<?>>> methodAnnotations = getMethodAnnotations(clazz);
        if (!CollectionUtil.isEmpty(methodAnnotations)){
            Set<Class<?>> methodAnnotationClazz = methodAnnotations.get(methodName);
            if (!CollectionUtil.isEmpty(methodAnnotationClazz)){
                return methodAnnotationClazz;
            }
        }
        return null;
    }

    /**
    *
    * 获取类所有方法的所有注解
    *
    * @params: [clazz, method]
    * @param: clazz			类对象
    * @param: method        方法对象
    * @return: {@link Map< String, Set< Class<?>>>}
    * @since: 2020/12/14
    **/
    public static Map<String, Set<Class<?>>> getMethodAnnotations(Class<?> clazz){
        Map<String, Set<Class<?>>> annotationsMap = new HashMap<>(16);
        Method[] methods = clazz.getMethods();
        for (Method mt : methods) {
            Annotation[] annotations = mt.getAnnotations();
            Set<Class<?>> annotationClazz = new HashSet<>();
            for (Annotation annotation : annotations) {
                annotationClazz.add(annotation.getClass());
            }
            annotationsMap.put(mt.getName(),annotationClazz);
        }
        return annotationsMap;
    }
}
