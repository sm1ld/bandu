//UserMapper.java
package com.sm1ld.mapper;

import com.sm1ld.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    /**
     * 用户注册：将用户注册信息插入数据库
     * @param user 用户注册信息
     */
    @Insert("INSERT INTO user(username,password,address,phoneNumber,email,createdAt,updatedAt) " +
            "VALUES (#{username},#{password},#{address},#{phoneNumber},#{email},now(),now())")
    void insertUser(User user);
    /**
     * 用户注册检查：根据用户名、电话、邮箱查询用户
     * @param username 用户名唯一
     * @param phoneNumber 电话唯一
     * @param email 邮箱唯一
     * @return 重复的用户信息
     */
    User findByParams(@Param("username") String username,
                      @Param("phoneNumber") String phoneNumber,
                      @Param("email") String email);

    /**
     * 用户登录：根据用户名和密码查询用户
     * @param user 用户登录信息
     * @return 用户登录信息
     */
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User getByNmPw(User user);

    /**
     * 用户查询：根据id查询用户
     * @param id 用户查询id
     * @return 用户查询信息
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    // 查询用户及其商品
    User findUserWithItems(Integer userId);

    /**
     * 用户修改：根据id将用户修改信息更新到数据库
     * @param user 用户修改信息
     */
    @Update("UPDATE user SET username = #{username}, password = #{password}, address = #{address}, " +
            "phoneNumber = #{phoneNumber}, email = #{email}, updatedAt = now() WHERE id = #{id}")
    void updateUser(User user);

    /**
     * 用户修改检查：根据用户名、电话、邮箱查询其他用户
     * @param username 用户名唯一
     * @param phoneNumber 电话唯一
     * @param email 邮箱唯一
     * @param id 当前id
     * @return 其他重复的用户信息
     */
    User findByParamsExId(@Param("username") String username,
                          @Param("phoneNumber") String phoneNumber,
                          @Param("email") String email,
                          @Param("id") Integer id);

    /**
     * 用户注销：根据id隐藏用户
     * @param id 用户隐藏id
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteUser(int id);
}


