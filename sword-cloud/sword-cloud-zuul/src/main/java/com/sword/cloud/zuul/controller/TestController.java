package com.sword.cloud.auth.controller;

import com.sword.cloud.auth.feign.UserClient;
import com.sword.cloud.model.UserDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class TestController {

    @Resource
    private UserClient userClient;

    @GetMapping(value = "users-anon/internal", params = "username")
    public UserDomain findByUsername(String username) {
        return userClient.findByUsername(username);
    }
}
