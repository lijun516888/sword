package com.sword.cloud.zuul.controller;

import com.google.common.collect.Maps;
import com.sword.cloud.model.UserDomain;
import com.sword.cloud.zuul.feign.Oauth2Client;
import com.sword.cloud.zuul.feign.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
public class TestController {

    @Resource
    private UserClient userClient;

    @Resource
    private Oauth2Client oauth2Client;

    @GetMapping(value = "/users-anon/internal", params = "username")
    public UserDomain findByUsername(String username) {
        return userClient.findByUsername(username);
    }

    @RequestMapping("/sys/login")
    public Map<String, Object> login() {
        Map<String, String> parameters = Maps.newHashMap();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, "system");
        parameters.put("client_secret", "system");
        parameters.put(OAuth2Utils.SCOPE, "app");
        parameters.put("username", "admin");
        parameters.put("password", "admin");
        Map<String, Object> userInfo = oauth2Client.postAccessToken(parameters);
        return userInfo;
    }

}
