package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.pojo.dto.AuthsInfo;
import com.hanson.pojo.vo.AuthGroup;
import com.hanson.pojo.vo.Authority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 权限service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:01
 **/
@Service
public interface AuthorityService {


    /*
    * @description:  获取所有的权限信息
    * @params: []
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/20
    */
    public CommonResult<List<Authority>> getAuthorities();


    /*
    * @description:  根据权限id获取权限信息
    * @params: [authId]
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/20
    */
    public CommonResult<AuthsInfo<AuthGroup>> getAuthorityInfo(Integer authId);


    /*
    * @description: 根据权限id批量查询权限信息
    * @params: [authorityIds] 要查询的权限id，保存在set集合里
    * @return: java.util.Set<com.hanson.pojo.Authority> 查询到的所有权限信息，以set集合返回
    * @Date: 2020/3/30
    */
    public Set<Authority> getAuthorityByIds(Set<Integer> authorityIds);



    /*
    * @description:  分页查询所有权限信息
    * @params: [pageSize, currentPage] index:开始索引，currentPage:当前页
    * @return: com.hanson.dto.PageBean<com.hanson.pojo.Authority> 权限信息，,封装为页面对象
    * @Date: 2020/4/19
    */
    /*
    * @description:  分页查询所有权限信息
    * @params: [pageSize, currentPage, search] index:开始索引，currentPage:当前页,search：搜索关键字
    * @return: com.hanson.common.api.PageResult<java.lang.Object>
    * @throws: Exception
    * @Date: 2020/9/20
    */
    public PageResult<List<AuthsInfo<AuthGroup>>> splitAuthority(Integer currentPage, Integer pageSize, String search);


    /*
    * @description: 新增权限信息
    * @params: [authority] 需要新增的权限信息
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @Date: 2020/4/19
    */
    public CommonResult<Integer> createAuthority(Authority authority);

    /*
    * @description: 修改权限信息
    * @params: [authority] 需要修改的权限
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @Date: 2020/4/19
    */
    public CommonResult<Integer> updateAuthority(Authority authority);


    /*
    * @description: 根据权限id删除权限
    * @params: [id] 权限id
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>
    * @Date: 2020/4/19
    */
    public CommonResult<Integer> deleteAuthority(Integer id, boolean flag);


    public CommonResult<List<AuthGroup>> getAuthGroups();

}
