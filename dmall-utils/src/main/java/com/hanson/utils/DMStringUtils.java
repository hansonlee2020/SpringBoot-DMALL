package com.hanson.utils;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @program: DreamMall
 * @description: 字符串处理工具类，封装各种字符串处理
 * @param:
 * @author: Hanson
 * @create: 2020-03-31 15:20
 **/
public class DMStringUtils {
    private DMStringUtils(){}


    /*
    * @description: 字符串分析截取处理 ，可以进行截取权限字符串
    * @params: [original] 源字符串
    * @return: java.lang.String 处理返回的目标字符串
    * @Date: 2020/3/31
    */
    public static String subStringBySymbol(String original){
        String symbol = "[";    //截取标记
        String target;   //目标字符串
        if(0 != original.length()){
            target = original.substring(original.indexOf(symbol) + 1,original.length() - 1);
            return target;  //返回目标字符串结果
        }
        return null;    //字符串不能为null
    }


    /*
    * @description: 格式json字符串，用于替换里面某个key-value
    * @params: [original, replace, replaceValue, index，isValueString]
    * original：源json字符串；replace：替换的key；
    * replaceValue：对应key替换的value；index：要替换的key-value对应的位置索引,温馨提示：索引从0开始！
    * isValueString：要替换的value是否为字符串
    * @return: java.lang.String 处理后的json字符串
    * @throws: Exception    NullPointerException,字符串索引超出字符串范围
    * @Date: 2020/4/6
    */
    public static String formatString(String original , String replace, String replaceValue, Integer index, boolean isValueString) throws NullPointerException {
        String result;//保存源字符串的处理结果
        String orig = original.substring(1, original.length() - 1);
        String[] items = orig.split(",");//拆分所有的item
        if(index < items.length){
            String[] temp = items[index].split(":");//temp[0]:item.name,temp[1]:item.value
            temp[0] = replace;
            temp[1] = replaceValue;
            StringBuilder stringBuilder = new StringBuilder();
            String tempResult;
            if (isValueString){     //替换的值为字符串类型
                tempResult = stringBuilder.append("\"").append(temp[0]).append("\":").append("\"").append(temp[1]).append("\"").toString();
            }else {                 //替换的值为非字符串类型
                tempResult = stringBuilder.append("\"").append(temp[0]).append("\":").append(temp[1]).toString();
            }
            items[index] = tempResult;
            StringBuilder itemsBuffer = new StringBuilder("{");
            for (String item : items) {
                itemsBuffer.append(item).append(",");
            }
            result = itemsBuffer.replace(itemsBuffer.length() - 1,itemsBuffer.length(),"").append("}").toString();
            return result;
        }else {
            throw  new NullPointerException("索引值超出源字符串分组范围！");
        }
    }


    /*
    * @description: 取出给定的字符串的指定key内容
    * @params: [original, key] original：源字符串；key：要取的内容对应的key
    * @return: java.lang.String key的内容
    * @Date: 2020/4/6
    */
    public static String fetchValue(String original, String key , boolean isStringType){
        String result;
        String temp1 = original.substring(original.indexOf("\"" + key + "\""));
        String temp2;
        if (temp1.indexOf(",") > 0){
            temp2 = temp1.substring(0, temp1.indexOf(","));//取出对应的key-value
        }else {
            if (temp1.indexOf("}") > 0){
                temp2 = temp1.substring(0, temp1.indexOf("}"));//取出对应的key-value
            }else {
                return "字符串不符合格式！";
            }
        }
        if(isStringType){
            result = temp2.substring(temp2.indexOf(":") + 1).replaceAll("\"","");//取出value
        }else {
            result = temp2.substring(temp2.indexOf(":") + 1);//取出value
        }
        return result;
    }


    /*
    * @description: 向json格式字符串添加一些属性和值，即添加key：value
    * @params: [original, name, value]
    * original：源json字符串；name：要添加的key；value：对应key的value
    * @return: java.lang.String 添加后的字符串
    * @Date: 2020/4/6
    */
    public static String increaseString(String original, String name, String value){
        String result;
        if(original.length() > 0 ){
            String substring = original.substring(original.indexOf("{") + 1);
            StringBuilder stringBuilder = new StringBuilder();
            result = (stringBuilder.append("{").append("\"").append(name).append("\":").append("\"").append(value).append("\"").append(",").append(substring)).toString();
            return result;
        }
        return "源字符串不能为空";
    }


    /* 
    * @description: 格式化生成的uuid
    * @params: [uuid] 需要格式的UUID类型的对象
    * @return: java.lang.String 格式后的uuid字符串
    * @Date: 2020/4/6
    */ 
    public static String formatUUID(UUID uuid){
        String id = uuid.toString();
        return id.replaceAll("-","");
    }


    /*
    * @description: 格式字符串，转换为Set<String>集合
    * @params: [original] 源字符串
    * @return: java.util.Set<java.lang.String> 返回 Set<String>集合
    * @Date: 2020/4/13
    */
    public static Set<String> formatToSet(String original){
        if ("".equals(original)){//源字符串为空，直接返回
            return null;
        }
        Set<String> result = new HashSet<>();//保存处理结果
        if (!original.contains(",")){//源字符串不含拆分标记直接返回
            result.add(original);
            return result;
        }
        String[] format = original.split(",");
        Collections.addAll(result, format);
        return result;
    }


    /*
    * @description:  格式字符串，转换为Set<Integer>集合
    * @params: [original] 源字符串
    * @return: java.util.Set<java.lang.Integer> 返回 Set<Integer>集合
    * @Date: 2020/4/24
    */
    public static Set<Integer> formatToSetInteger(String original){
        if ("".equals(original)){//源字符串为空，直接返回
            return null;
        }
        Set<Integer> result = new HashSet<>();//保存处理结果
        if (!original.contains(",")){//源字符串不含拆分标记直接返回
            result.add(Integer.valueOf(original));
            return result;
        }
        String[] format = original.split(",");
        for (String s : format) {
            result.add(Integer.valueOf(s));
        }
        return result;
    }
}
