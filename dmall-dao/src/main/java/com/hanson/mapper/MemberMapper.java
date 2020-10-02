package com.hanson.mapper;

import com.hanson.pojo.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 用户接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-20 20:35
 **/
@Mapper
@Repository
public interface MemberMapper {


    /*
    * @description: 查询全部用户数据
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Member> 用户信息，list列表
    * @Date: 2020/4/20
    */
    public List<Member> doGetMembers();


    /*
    * @description: 根据用户id查询用户
    * @params: [id] 用户id
    * @return: com.hanson.pojo.Member 查询成功返回该用户信息，否则返回0
    * @Date: 2020/4/20
    */
    public Member doGetMemberById(@Param("memberId") Integer id);


    /*
    * @description: 根据用户名查询用户
    * @params: [name] 用户名
    * @return: com.hanson.pojo.Member 查询成功返回该用户，否则返回0
    * @Date: 2020/4/20
    */
    public Member doGetMemberByName(@Param("memberName") String name);


    /*
    * @description: 新增用户
    * @params: [member] 用户
    * @return: java.lang.Integer 新增成功返回1，否则抛出异常信息
    * @throws: Exception SQL执行Exception 违反主键或唯一性异常
    * @Date: 2020/4/20
    */
    public Integer doCreateMember(Member member);
    


    /*
    * @description:  分页模糊查询用户数据
    * @params: [start, pageSize, key]
    * start：分页开始索引, pageSize：分页大小, key：模糊查询关键字,state：查询的用户状态
    * @return: java.util.List<com.hanson.pojo.Member> 用户数据
    * @Date: 2020/4/21
    */
    public List<Member> doSplitMember(@Param("startIndex") Integer start, @Param("pageSize") Integer pageSize, @Param("memberName") String key,@Param("state")@NotNull Integer state);


    /*
     * @description:  分页模糊查询回收站用户数据
     * @params: [start, pageSize, key]
     * start：分页开始索引, pageSize：分页大小, key：模糊查询关键字,state：查询的用户状态
     * @return: java.util.List<com.hanson.pojo.Member> 用户数据
     * @Date: 2020/4/21
     */
    public List<Member> doSplitMemberTrash(@Param("startIndex") Integer start, @Param("pageSize") Integer pageSize, @Param("memberName") String key,@Param("state")@NotNull Integer state);


    /*
    * @description: 统计用户数据
    * @params: [key,state] 模糊查询关键字,state：查询的用户状态
    * @return: java.lang.Integer
    * @Date: 2020/4/21
    */
    public Integer doCounts(@Param("state") Integer state);


    /*
    * @description: 模糊查询用户名列,用于模糊统计
    * @params: [key,state] 查询的关键字,state：查询的用户状态
    * @return: java.util.List<com.hanson.pojo.Member> 用户，list集合
    * @Date: 2020/4/21
    */
    public List<Member> doSearchMember(@Param("memberName") String key,@Param("state")@NotNull Integer state);


    /*
     * @description: 模糊查询回收用户名列，用于模糊统计
     * @params: [key,state] 查询的关键字,state：查询的用户状态
     * @return: java.util.List<com.hanson.pojo.Member> 用户，list集合
     * @Date: 2020/4/21
     */
    public List<Member> doSearchMemberTrash(@Param("memberName") String key,@Param("state")@NotNull Integer state);


    /*
    * @description: 根据用户id，更新用户的状态
    * @params: [id, state] id：用户id，state：用户状态
    * @return: java.lang.Integer 更新成功返回1，否则返回0
    * @Date: 2020/4/21
    */
    public Integer doUpdateMemberState(@Param("memberId") Integer id, @Param("state")@NotNull Integer state);


    /*
    * @description: 删除用户
    * @params: [id]
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/14
    */
    public Integer doDeleteMember(@Param("memberId") Integer id);


    /*
    * @description: 批量删除用户
    * @params: [ids] 要删除的id，Set集合
    * @return: java.lang.Integer 成功删除的数据数量,删除失败返回0
    * @Date: 2020/4/22
    */
    public Integer doBatchDeleteMembers(@Param("ids") Set<Integer> ids);




    /*
     * @description: 批量修改用户状态
     * @params: [ids,state] 要回收的id，Set集合,state：用户状态
     * @return: java.lang.Integer 成功的数据数量,失败返回0
     * @Date: 2020/4/22
     */
    public Integer doBatchUpdateMembers(@Param("ids") Set<Integer> ids,@Param("state")@NotNull Integer state);


    /*
    * @description: 初始化用户表
    * @params: []
    * @return: java.lang.Integer
    * @Date: 2020/5/2
    */
    public Integer doInitMember();


    /*
    * @description: 根据状态统计回收站数据量
    * @params: [state]
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doTrashCount(@Param("state")@NotNull Integer state);
}
