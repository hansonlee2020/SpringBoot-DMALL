package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.pojo.vo.AuthGroup;

import java.util.List;

/**
 * @program: manager
 * @description: 权限组接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-21 14:06
 **/
public interface AuthGroupService {

    public CommonResult<AuthGroup> authGroupInfo(Integer groupId);
    public PageResult<List<AuthGroup>> authGroupSplit(Integer page, Integer limit, String key);
    public CommonResult<Integer> authGroupCreate(String groupName);
    public CommonResult<Integer> authGroupUpdate(Integer groupId, String groupName);
    public CommonResult<Integer> authGroupDeleteById(Integer groupId, boolean isChecked);
    public CommonResult<Integer> authGroupDeleteByName(String groupName, boolean isChecked);

}
