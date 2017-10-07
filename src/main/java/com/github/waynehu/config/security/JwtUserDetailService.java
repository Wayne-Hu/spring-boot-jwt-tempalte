package com.github.waynehu.config.security;

import com.github.waynehu.mapper.UserMapper;
import com.github.waynehu.mapper.UserRoleMapper;
import com.github.waynehu.module.User;
import com.github.waynehu.module.UserRole;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailService implements UserDetailsService {
    private UserMapper userMapper;
    private UserRoleMapper userRoleMapper;

    @Autowired
    public JwtUserDetailService(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * find User by username from anywhere you want, for example database, memory or file system.
     *
     * @param username
     * @return UserDetails instance
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUserName(username);
        return new JwtUserDetail(user.getId(),
                username,
                user.getPassword(),
                getAuthorities(ImmutableList.of(userRoleMapper.findById(user.getUserRoleId()))));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
                .collect(Collectors.toList());
    }
}
