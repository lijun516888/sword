package com.sword.app.controller;

import com.sword.app.domain.OrderDomain;
import com.sword.app.domain.UserDomain;
import com.sword.app.service.OrderService;
import com.sword.app.service.UserService;
import com.sword.core.dto.JsonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/sword")
public class SwordController {

    @Resource
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "user")
    public JsonEntity<UserDomain> doGetUser() throws Exception {
        JsonEntity<UserDomain> result = new JsonEntity<>();
        List<OrderDomain> all = orderService.getAll(0L);
        return result;
    }

}
