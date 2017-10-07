package com.github.waynehu.mapper;

import com.github.waynehu.module.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleMapper {
    @Select("SELECT * FROM USER_ROLE WHERE id = #{id}")
    UserRole findById(String id);
}
