package com.sword.cloud.user.controller;

import com.sword.cloud.model.user.LoginAppUser;
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

    @GetMapping(value = "/users-anon/internal", params = "username")
    public LoginAppUser findByUsername(String username) {
        LoginAppUser loginAppUser = userService.findByUsername(username);
        return loginAppUser;
    }

    @GetMapping(value = "/users/external", params = "age")
    public LoginAppUser findByUserage(String age) {
        System.out.println("=================");
        return userService.findByUsername(age);
    }

}
