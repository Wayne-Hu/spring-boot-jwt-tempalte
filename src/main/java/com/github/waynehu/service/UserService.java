package com.github.waynehu.service;

import com.github.waynehu.config.security.JwtTokenUtil;
import com.github.waynehu.mapper.UserMapper;
import com.github.waynehu.module.User;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    private UserMapper userMapper;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserService(UserMapper userMapper, JwtTokenUtil jwtTokenUtil) {
        this.userMapper = userMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String findById(String id) {
        return null;
    }

    public String register(String username, String password, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPwd = passwordEncoder.encode(password);
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(encodedPwd);
        user.setEmail(email);
        user.setUserRoleId("2");
        userMapper.save(user);

        Map<String, Object> claims = Maps.newHashMap();
        claims.put("username", username);
        claims.put("email", email);
        String token = jwtTokenUtil.generateToken(claims);
        return token;
    }
}
