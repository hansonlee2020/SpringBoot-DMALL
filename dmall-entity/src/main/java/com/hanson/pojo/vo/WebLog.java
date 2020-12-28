package com.hanson.pojo.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @CLassName WebLog
 * @Description 系统接口操作日志记录表实体类
 * @Author li hao xin
 * @Date 2020/11/30 16:11
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "系统接口操作日志记录表实体类")
public class WebLog implements Serializable {

    @ApiModelProperty(name = "id",value = "主键id",dataType = "Long")
    private Long id;

    @ApiModelProperty(name = "ip",value = "ip地址",dataType = "String")
    private String ip;

    @ApiModelProperty(name = "ipAddress",value = "ip地址解析",dataType = "String")
    private String ipAddress;

    @ApiModelProperty(name = "logName",value = "操作日志名称",dataType = "String")
    private String logName;

    @ApiModelProperty(name = "createBy",value = "日志创建人",dataType = "String")
    private String createBy;

    @ApiModelProperty(name = "createTime",value = "日志创建时间",dataType = "Date")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",value = "日志更新时间",dataType = "Date")
    private Date updateTime;

    @ApiModelProperty(name = "requestArgs",value = "接口请求参数",dataType = "JSONObject")
    private JSONObject requestArgs;

    @ApiModelProperty(name = "response",value = "接口响应参数",dataType = "JSONObject")
    private JSONObject response;

    @ApiModelProperty(name = "exception",value = "异常信息",dataType = "String")
    private String exception;

    @ApiModelProperty(name = "url",value = "请求路径",dataType = "String")
    private String url;

    @ApiModelProperty(name = "timeConsuming",value = "请求耗时",dataType = "int")
    private Long timeConsuming;

    @ApiModelProperty(name = "status",value = "日志状态，0-正常，1-删除",dataType = "Integer")
    private Integer status;

    /**
     * 设置请求参数
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        if (paramMap == null) {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            String obj = StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "****************" : paramValue;
            params.put(key,obj);
        }
        this.requestArgs = new JSONObject(params);
    }

    /*
    * 设置响应参数
    *
    * @params: [obj]
    * @return: void
    * @Date: 2020/12/9
    */
    public void setResponseResult(Object obj){
        if (obj instanceof String){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("viewUrl",obj);
            jsonObject.put("isReturnJson",false);
            this.response = jsonObject;
        }else {
            this.response = JSONObject.parseObject(JSONObject.toJSONString(obj, true));
        }
    }
}
