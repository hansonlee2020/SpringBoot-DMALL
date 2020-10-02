package com.hanson.mapper;

import com.hanson.pojo.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: manager
 * @description: 用户dao层接口
 * @param:
 * @author: Hanson
 * @create: 2020-09-04 18:14
 **/
@Mapper
@Repository
public interface UserMapper {
    /*q
    ------------------------------------------后台登陆----------------------------------
     */

    /*
     * @description: 根据需要登陆的用户对象的用户名查找，验证该用户的合法性 ，并获取该用户的信息
     * @params: [login] 需要登陆的用户对象
     * @return: com.hanson.pojo.User 如果用户存在，以对象方式返回这个用户的信息，否则返回null
     * @Date: 2020/3/20
     */
    public User doGetLoginUser(User login);

    /*
     * @description: 根据登陆用户名查找用户信息
     * @params: [userName] 用户名
     * @return: com.hanson.pojo.User 返回用户信息
     * @Date: 2020/3/30
     */
    public User doGetLoginUserByName(String userName);


    /*q
    ------------------------------------------后台管理----------------------------------
     */

    /*
     * @description: 查询全部系统管理员
     * @params: []
     * @return: java.util.List<com.hanson.pojo.User> 系统管理员list
     * @Date: 2020/4/23
     */
    public List<User> doGetUsers();

    /*
     * @description: 根据系统管理员用户id查询系统管理员信息
     * @params: [uid] 管理员用户id
     * @return: com.hanson.pojo.User 系统管理员信息
     * @Date: 2020/4/23
     */
    public User doGetUserById(@Param("userId") String uid);


    /*
     * @description: 根据系统管理员用户名查询系统管理员信息
     * @params: [name] 管理员用户名
     * @return: com.hanson.pojo.User 系统管理员信息
     * @Date: 2020/4/23
     */
    public User doGetUserByName(@Param("userName") String name);


    /*
     * @description: 新建管理员用户
     * @params: [user] 管理员信息
     * @return: java.lang.Integer 新建成功返回1，否则返回0或抛出异常信息
     * @throws: Exception  SQL执行异常，违反唯一约束异常
     * @Date: 2020/4/23
     */
    public Integer doCreateUser(User user);


    /*
     * @description: 根据管理员用户id更新系统管理员用户信息
     * @params: [user] 管理员用户
     * @return: java.lang.Integer 修改成功返回1，否则返回0或抛出异常信息
     * @throws: Exception  SQL执行异常，违反唯一约束异常
     * @Date: 2020/4/23
     */
    public Integer doUpdateUser(@Param("user") User user);



    /*
     * @description: 根据管理员用户id删除管理员信息
     * @params: [uid] 管理员用户id
     * @return: java.lang.Integer 删除成功返回1，否则返回0或抛出异常信息
     * @throws: Exception  SQL执行异常，外键使用异常
     * @Date: 2020/4/23
     */
    public Integer doDeleteUser(@Param("userId") String uid);



    /*
     * @description: 统计管理员账号
     * @params: []
     * @return: java.lang.Integer
     * @Date: 2020/4/23
     */
    public Integer doCounts();


    /*
     * @description: 分页模糊查询管理员信息
     * @params: [startIndex, pageSize] startIndex：开始索引, pageSize：页面大小,key：模糊查询关键字
     * @return: java.util.List<com.hanson.pojo.User>
     * @Date: 2020/4/23
     */
    public List<User> doUserSplit(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize,@Param("searchKey") String key);


    /*
     * @description: 模糊查询管理员用户
     * @params: [key] 模糊查询的关键字
     * @return: java.util.List<com.hanson.pojo.User> 管理员list列表
     * @Date: 2020/4/23
     */
    public List<User> doSearchUserByName(@Param("userName") String key);


    /*
    * @description: 根据管理员id更新管理员状态
    * @params: [uid, state] uid：管理员id，state：管理员状态
    * @return: java.lang.Integer
    * @throws: Exception
    * @Date: 2020/9/15
    */
    public Integer doUpdateUserState(@Param("uid") String uid, @Param("state")@NotNull Integer state);
}
