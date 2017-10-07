package com.github.waynehu.mapper;

import com.github.waynehu.module.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE username = #{username}")
    @Results(id = "User", value = {
            @Result(column = "user_role_id", property = "userRoleId")
    })
    User findByUserName(String username);

    @Insert("INSERT INTO USER (id, username, password, email, user_role_id) " +
            "VALUE(#{id}, #{username}, #{password}, #{email}, #{userRoleId})")
    void save(User user);
}
