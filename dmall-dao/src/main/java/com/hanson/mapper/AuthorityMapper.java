package com.hanson.mapper;

import com.hanson.pojo.vo.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: dao层权限接口
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 15:34
 **/
@Mapper
@Repository
public interface AuthorityMapper {


    /*
    * @description: 获取所有权限信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Authority> 所有权限对象集合，保存为List
    * @Date: 2020/3/30
    */
    public List<Authority> doGetAuthorities();



    /*
    * @description: 查询全部权限的id和描述
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Authority>
     * @Date: 2020/4/24
    */
    public List<Authority> doGetAuthority();



    /*
    * @description: 根据权限id查找权限信息
    * @params: []
    * @return: com.hanson.pojo.Authority 查询到的权限
    * @Date: 2020/3/30
    */
    public Authority doGetAuthorityById(@Param("id") Integer authorityId);


    /*
    * @description: 根据权限名查询权限信息
    * @params: [name] 要查询的权限名
    * @return: com.hanson.pojo.Authority  该权限名的权限信息
    * @Date: 2020/4/19
    */
    public Authority doGetAuthorityByName(@Param("authorityName") String name);


    /*
    * @description: 根据权限的资源字段名查询权限信息
    * @params: [field] 要查询的资源字段
    * @return: com.hanson.pojo.Authority 该资源字段的权限信息
    * @Date: 2020/4/19
    */
    public Authority doGetAuthorityByField(@Param("authorityField") String field);


    /*
    * @description: 根据权限组ID获取权限信息
    * @params: [groupId] 权限组ID
    * @return: java.util.List<com.hanson.pojo.vo.Authority>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<Authority> doGetAuthorityByGroup(@Param("gid")Integer groupId);


    /*
    * @description: 根据权限id批量查询权限信息
    * @params: [authorityId] 要查询的权限id，存放在Set集合里
    * @return: java.util.Set<com.hanson.pojo.Authority> 查询到的所有权限信息,保存在set集合里
    * @Date: 2020/3/30
    */
    public Set<Authority> doGetAuthoritiesByIds(@Param("ids") Set<Integer> authorityIds);




    /*
    * @description: 统计权限数据量
    * @params: []
    * @return: java.lang.Integer 返回权限数据量
     * @Date: 2020/4/19
    */
    public Integer doCounts();



    /*
     * @description:  统计权限id最大值,用于自动设置新的权限id
     * @params: []
     * @return: java.lang.Integer 最大的权限id
     * @Date: 2020/4/19
     */
    public Integer doCountsMaxId();




    /*
    * @description: 新增权限信息
    * @params: [authority] 需要新增的权限信息
    * @return: java.lang.Integer 新增成功返回1，否则返回异常信息
    * @throws: Exception SQL执行异常：违反主键唯一约束或者违反唯一约束异常
    * @Date: 2020/4/19
    */
    public Integer doCreateAuthority(Authority authority);


    /*
    * @description: 修改权限信息
    * @params: [authority] 需要修改的权限
    * @return: java.lang.Integer 修改成功返回1，否则返回0或者抛出异常信息
    * @throws: Exception SQL执行异常：违反唯一约束异常
    * @Date: 2020/4/19
    */
    public Integer doUpdateAuthority(Authority authority);


    /*
    * @description: 根据权限id删除权限信息
    * @params: [id] 需要删除的权限id
    * @return: java.lang.Integer 删除成功返回1，否则返回0或者抛出异常信息
    * @throws: Exception SQL执行异常：作为外键使用异常
    * @Date: 2020/4/19
    */
    public Integer doDeleteAuthorityById(@Param("aid") Integer authorityId);


    /*
    * @description: 分页模糊查询
    * @params: [startIndex, pageSize, key] startIndex：开始索引, pageSize：分页大小, key：关键字
    * @return: java.util.List<com.hanson.pojo.Authority>
    * @Date: 2020/5/12
    */
    public List<Authority> doSplitAuthority(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize, @Param("key") String key);



    /*
    * @description: 模糊查询
    * @params: [key] key：模糊查询关键字
    * @return: java.util.List<com.hanson.pojo.Authority>
    * @Date: 2020/5/12
    */
    public List<Authority> doSearchAuthority(@Param("key") String key);


    /*
    * @description: 初始化权限数据
    * @params: []
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/5/13
    */
    public Integer doInitAuthority();
}
