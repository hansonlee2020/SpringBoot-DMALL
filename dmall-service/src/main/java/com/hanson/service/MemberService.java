package com.hanson.service;

import com.hanson.common.api.CommonResult;
import com.hanson.common.api.PageResult;
import com.hanson.pojo.dto.MemberTable;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 用户service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-21 13:55
 **/
public interface MemberService {

    /*
    * @description: 分页模糊查询用户
    * @params: [pageSize, currentPage, key] pageSize：分页大小, currentPage：当前页数, key：搜索关键字,state：用户状态
    * @return: com.hanson.common.api.PageResult<java.util.List<com.hanson.pojo.dto.MemberTable>>  用户数据，封装为pageBean对象
    * @Date: 2020/9/12
    */
    public PageResult<List<MemberTable>> splitMember(Integer pageSize, Integer currentPage, String key,Integer state);


    /*
     * @description: 分页模糊查询回收站用户
     * @params: [pageSize, currentPage, key] pageSize：分页大小, currentPage：当前页数, key：搜索关键字,state：用户状态
     * @return: com.hanson.common.api.PageResult<java.util.List<com.hanson.pojo.dto.MemberTable>>  用户数据，封装为pageBean对象
     * @Date: 2020/9/12
     */
    public PageResult<List<MemberTable>> splitMemberTrash(Integer pageSize, Integer currentPage, String key,Integer state);


    /*
    * @description: 根据用户id更新用户状态
    * @params: [memberId, state] memberId：用户id，state：用户状态
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>  返回处理消息
    * @Date: 2020/4/21
    */
    public CommonResult<Integer> updateMemberState(Integer memberId, Integer state);


    /*
    * @description: 根据用户id删除用户
    * @params: [memberId] 用户id
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>  返回处理消息
    * @Date: 2020/4/22
    */
    public CommonResult<Integer> deleteMember(Integer memberId);


    /*
    * @description: 批量删除用户
    * @params: [ids] 要删除的用户id集合
    * @return: com.hanson.common.api.CommonResult<java.lang.Object>  返回删除处理信息
    * @Date: 2020/4/22
    */
    public CommonResult<Integer> batchDeleteMembers(List<Integer> ids);




    /*
     * @description: 批量更新用户的状态
     * @params: [ids] 要回收的用户id集合,state：用户状态
     * @return: com.hanson.common.api.CommonResult<java.lang.Object>  返回回收处理信息
     * @Date: 2020/4/22
     */
    public CommonResult<Integer> batchUpdateMembers(List<Integer> ids, Integer state);
}
