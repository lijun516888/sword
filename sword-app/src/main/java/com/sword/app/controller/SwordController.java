package com.sword.app.controller;

import com.google.common.collect.Maps;
import com.sword.app.domain.UserDomain;
import com.sword.app.service.UserService;
import com.sword.core.dto.JsonEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sword")
public class SwordController {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "user")
    public JsonEntity<UserDomain> doGetUser() {
        JsonEntity<UserDomain> result = new JsonEntity<>();
        Map<String, Object> map = Maps.newHashMap();
        List<UserDomain> userDomains = userService.list(map);
        result.setEntity(userDomains.get(0));
        return result;
    }

}
