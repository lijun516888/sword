package com.sword.cloud.user.controller;

import com.sword.cloud.user.domain.UserDomain;
import com.sword.cloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users-anon/internal", params = "username")
    public UserDomain findByUsername(String username) {
        return userService.findByUsername(username);
    }

}
