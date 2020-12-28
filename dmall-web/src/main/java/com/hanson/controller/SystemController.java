package com.hanson.controller;

import com.hanson.annotation.WebLogIgnore;
import com.hanson.common.api.CommonResult;
import com.hanson.common.api.ResultCode;
import com.hanson.pojo.dto.SystemInfo;
import com.hanson.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: manager
 * @description: 获取系统信息接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-07 18:41
 **/
@Api(tags = "系统信息接口")
@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private SystemService systemService;

    //获取系统信息信息
    @ApiOperation("获取系统信息信息")
    @PostMapping("/run/infos")
    @WebLogIgnore
    @ResponseBody
    public CommonResult<SystemInfo> getSystemInfos(){
        String host = systemService.getSysHost();
        String system = systemService.getSystemEdition();
        String core = systemService.getCore();
        String jvmMemory = systemService.getJvmMemory();
        String jvmFreeMemory = systemService.getJvmFreeMemory();
        return new CommonResult<>(ResultCode.SUCCESS, new SystemInfo(host, system, core, jvmMemory, jvmFreeMemory));
    }
}
