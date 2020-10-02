package com.hanson.controller;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.common.api.ResultCode;
import com.hanson.common.constant.Constant;
import com.hanson.pojo.dto.MemberTable;
import com.hanson.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: manager
 * @description: 会员管理接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-14 15:46
 **/
@Api(tags = "会员管理接口")
@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /*q
    ------------------------会员列表分页-------------------
     */

    //打开会员管理页面
    @ApiOperation("打开会员列表页面")
    @GetMapping("/lists")
    public String memberList(){
        return "member/member-list";
    }

    //分页显示会员数据
    @ApiOperation("会员列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @PostMapping("/lists")
    @ResponseBody
    public PageResult<List<MemberTable>> memberSplit(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page, String search){
        PageResult<List<MemberTable>> res = null;
        try {
            res = memberService.splitMember(limit, page, search, Constant.RECYCLE_STATE);//状态取反
            res.setMsg("分页成功");
            return res;
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            PageResult<List<MemberTable>> error = new PageResult<>(ResultCode.FAILED, 0, null);
            error.setMsg(msg);
            return error;
        }
    }

    /*q
    ---------------------------会员启用、停用、修改密码、回收---------
     */

    //启用会员
    @ApiOperation("启用会员")
    @ApiImplicitParam(name = "mid",value = "会员ID",required = true,dataType = "Integer")
    @PostMapping("/on")
    @ResponseBody
    public CommonResult<Integer> memberOn(@RequestParam("mid") Integer mid){
        CommonResult<Integer> res = memberService.updateMemberState(mid, Constant.ENABLE_STATE);
        Integer num = res.getData();
        if (num == 0) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent("启用失败");
        }else res.setContent("已启用");
        return res;
    }

    //停用会员
    @ApiOperation("停用会员")
    @ApiImplicitParam(name = "mid",value = "会员ID",required = true,dataType = "Integer")
    @PostMapping("/off")
    @ResponseBody
    public CommonResult<Integer> memberOff(@RequestParam("mid") Integer mid){
        CommonResult<Integer> res = memberService.updateMemberState(mid, Constant.DISABLE_STATE);
        Integer num = res.getData();
        if (num == 0) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent("停用失败");
        }else res.setContent("已停用");
        return res;
    }

    //回收会员
    @ApiOperation("回收会员")
    @ApiImplicitParam(name = "mid",value = "会员ID",required = true,dataType = "Integer")
    @PostMapping("/recy")
    @ResponseBody
    public CommonResult<Integer> memberRecycle(@RequestParam("mid") Integer mid){
        CommonResult<Integer> res = memberService.updateMemberState(mid, Constant.RECYCLE_STATE);
        Integer num = res.getData();
        if (num == 0) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent("回收会员失败");
        }else res.setContent("会员已回收");
        return res;
    }

    //恢复会员
    @ApiOperation("恢复会员")
    @ApiImplicitParam(name = "mid",value = "会员ID",required = true,dataType = "Integer")
    @PostMapping("/recovery")
    @ResponseBody
    public CommonResult<Integer> memberRecovery(@RequestParam("mid") Integer mid){
        CommonResult<Integer> res = memberService.updateMemberState(mid, Constant.DISABLE_STATE);
        Integer num = res.getData();
        if (num < 1) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent(ResultCode.FAILED.getContent());
        }else res.setContent(ResultCode.SUCCESS.getContent());
        return res;
    }

    //删除会员
    @ApiOperation("删除会员")
    @ApiImplicitParam(name = "mid",value = "会员ID",required = true,dataType = "Integer")
    @PostMapping("/del")
    @ResponseBody
    public CommonResult<Integer> memberDelete(@RequestParam("mid") Integer mid){
        CommonResult<Integer> res = memberService.deleteMember(mid);
        Integer num =  res.getData();
        if (num < 0) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent(ResultCode.FAILED.getContent());
        }else res.setContent(ResultCode.SUCCESS.getContent());
        return res;
    }


    /*q
    --------------------------------批量操作：回收、删除会员---------------
     */

    //回收会员
    @ApiOperation("批量回收会员")
    @ApiImplicitParam(name = "ids",value = "会员ID组",required = true,dataType = "List<Integer>")
    @PostMapping("/recys")
    @ResponseBody
    public CommonResult<Integer> memberRecycles(@RequestBody List<Integer> ids){
        CommonResult<Integer> res = memberService.batchUpdateMembers(ids, Constant.RECYCLE_STATE);
        Integer num = res.getData();
        if (num < 1) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent(ResultCode.FAILED.getContent());
        }else res.setContent("已回收 " + num + " 位会员");
        return res;
    }

    //恢复会员
    @ApiOperation("批量恢复会员")
    @ApiImplicitParam(name = "ids",value = "会员ID组",required = true,dataType = "List<Integer>")
    @PostMapping("/recs")
    @ResponseBody
    public CommonResult<Integer> memberRecoveries(@RequestBody List<Integer> ids){
        CommonResult<Integer> res = memberService.batchUpdateMembers(ids, Constant.DISABLE_STATE);
        Integer num = res.getData();
        if (num < 1) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent(ResultCode.FAILED.getContent());
        }else res.setContent("已恢复 " + num + " 位会员");
        return res;
    }

    //删除会员
    @ApiOperation("批量删除会员")
    @ApiImplicitParam(name = "ids",value = "会员ID组",required = true,dataType = "List<Integer>")
    @PostMapping("/dels")
    @ResponseBody
    public CommonResult<Integer> memberDeletes(@RequestBody List<Integer> ids){
        CommonResult<Integer> res = memberService.batchDeleteMembers(ids);
        Integer num = res.getData();
        if (num < 1) {
            res.setType(ResultCode.FAILED.getType());
            res.setContent(ResultCode.FAILED.getContent());
        }else res.setContent("已清理 " + num + " 位会员");
        return res;
    }


    /*q
    --------------------------------会员回收站-------------------
     */
    //打开会员回收站
    @ApiOperation("打开会员回收站页面")
    @GetMapping("/recycle/bin")
    public String memberRubbishBin(){
        return "member/member-bin";
    }

    //分页获取回收站数据
    @ApiOperation("会员回收站分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit",value = "分页大小",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "page",value = "当前页码",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "search",value = "搜索关键字",required = false,dataType = "String")
    })
    @PostMapping("/recycle/bin")
    @ResponseBody
    public PageResult<List<MemberTable>> memberBinSplit(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page, String search){
        try {
            PageResult<List<MemberTable>> memberTrash = memberService.splitMemberTrash(limit, page, search, Constant.RECYCLE_STATE);
            memberTrash.setMsg("分页成功");
            return memberTrash;
        } catch (NullPointerException e) {
            return new PageResult<>(500,e.getMessage(),0,null);
        } catch (Exception e){
            return new PageResult<>(500,"系统查询出错",0,null);
        }
    }
}
