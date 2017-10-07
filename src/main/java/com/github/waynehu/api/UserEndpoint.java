package com.github.waynehu.api;

import com.github.waynehu.api.form.UserForm;
import com.github.waynehu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    private UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/users/{id}")
    public String user(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PostMapping("/api/v1/users")
    public String register(@RequestBody UserForm userForm) {
        LOGGER.debug("User {} register", userForm.getUsername());
        String token = userService.register(userForm.getUsername(), userForm.getPassword(), userForm.getEmail());
        return token;
    }
}
