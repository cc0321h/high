package org.explore.high.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.explore.high.entity.User;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
    
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(String email);
    
    /**
     * 插入用户信息
     * @param user 用户信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO user(username, email, password, role, status) VALUES(#{username}, #{email}, #{password}, #{role}, #{status})")
    int insert(User user);
    
    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 影响的行数
     */
    @Update("UPDATE user SET username = #{username}, email = #{email}, password = #{password}, role = #{role}, status = #{status} WHERE id = #{id}")
    int update(User user);
}