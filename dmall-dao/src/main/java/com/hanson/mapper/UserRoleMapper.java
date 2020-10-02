package com.hanson.mapper;

import com.hanson.pojo.vo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @program: manager
 * @description: 用户角色接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-15 19:03
 **/
@Mapper
@Repository
public interface UserRoleMapper {


    /*
    * @description: 查询用户全部角色
    * @params: []
    * @return: java.util.List<com.hanson.pojo.vo.UserRole>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<UserRole> doGetUserRoles();

    /*
    * @description: 根据用户ID查询用户-角色
    * @params: [uid] 用户ID
    * @return: java.util.List<com.hanson.pojo.vo.UserRole>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<UserRole> doGetUserRolesByUid(@Param("uid") String uid);

    /*
    * @description: 根据角色ID查询用户-角色
    * @params: [rid] 角色ID
    * @return: java.util.List<com.hanson.pojo.vo.UserRole>
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public List<UserRole> doGetUserRolesByRid(@Param("rid") Integer rid);

    /*
    * @description: 创建用户-角色
    * @params: [userRole]
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCreateUserRole(UserRole userRole);

    /*
    * @description: 更新用户角色
    * @params: [userRole] 用户角色对象
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doUpdateUserRole(@Param("ur") UserRole userRole);

    /*
    * @description: 精准删除用户-角色
    * @params: [uid, rid]
    * uid：用户ID
    * rid：角色ID
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteUserRoleWithRid(@Param("uid") String uid,@Param("rid") Integer rid);

    /*
    * @description:  根据用户ID删除用户角色
    * @params: [uid] 用户ID
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteUserRole(@Param("uid") String uid);


    /*
    * @description: 创建用户角色
    * @params: [uid, rids]
    * uid：用户ID
    * rids：角色ID集合
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doCreateUserRoles(@Param("uid") String uid, @Param("rids")Set<Integer> rids);

    /*
    * @description: 删除用户全部角色
    * @params: [uid, rids]
    * uid：用户ID
    * rids：角色ID集合
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/30
    */
    public Integer doDeleteUserRoles(@Param("uid") String uid, @Param("rids")Set<Integer> rids);
    public Integer doCounts();
}
