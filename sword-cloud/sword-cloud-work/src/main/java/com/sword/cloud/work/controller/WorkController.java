package com.sword.cloud.work.controller;

import com.sword.cloud.model.user.LoginAppUser;
import com.sword.cloud.work.feign.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
public class WorkController {

    @Resource
    private UserClient userClient;

    @GetMapping(value = "/work/doSave")
    public void doSave(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("===1111111====");
        System.out.println("===2222222====");
        System.out.println("===3333333====");
        System.out.println("===8888888====");
        System.out.println("===99999994324234====");
        int a = 0;
        int b = 1;
        int c = a + b;
        LoginAppUser loginAppUser = userClient.findByUsername("admin");
    }

}
