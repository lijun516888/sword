package com.sword.cloud.work.controller;

import com.sword.cloud.model.user.LoginAppUser;
import com.sword.cloud.work.feign.Oauth2Client;
import com.sword.cloud.work.feign.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
public class WorkController {

    @Resource
    private UserClient userClient;

    @Resource
    private Oauth2Client oauth2Client;

    @Resource
    private UserInfoTokenServices tokenServices;

    @RequestMapping(value = "/work/doSave")
    public LoginAppUser doSave(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        LoginAppUser loginAppUser = userClient.findByUsername("admin");
        String accessToken = parameterMap.get("access_token")[0]+"";
        Authentication authentication = tokenServices.loadAuthentication(accessToken);
        Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
        Object details = userAuthentication.getDetails();
        Object principal = ((Map) details).get("principal");
        return loginAppUser;
    }

}
