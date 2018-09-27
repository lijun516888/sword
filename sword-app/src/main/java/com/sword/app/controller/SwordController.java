package com.sword.app.controller;

import com.google.common.collect.Maps;
import com.sword.app.domain.OrderDomain;
import com.sword.app.domain.UserDomain;
import com.sword.app.mapper.OrderMapper;
import com.sword.app.service.OrderService;
import com.sword.app.service.UserService;
import com.sword.core.dto.JsonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sword")
public class SwordController {

    @Resource
    private UserService userService;

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private DataSource sjDataSource;

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "user")
    public JsonEntity<UserDomain> doGetUser() {
        JsonEntity<UserDomain> result = new JsonEntity<>();
        /*Map<String, Object> map = Maps.newHashMap();
        List<UserDomain> userDomains = userService.list(map);
        result.setEntity(userDomains.get(0));*/

        /*UserDomain userDomain = new UserDomain();
        userDomain.setPassword("111111");
        userDomain.setStatus(1);
        userDomain.setUserName("test");
        userService.save(userDomain);*/

        /*OrderDomain var = new OrderDomain();
        var.setName("C");
        var.setTid(0L);
        orderService.save(var);*/

        Map<String, Object> map = Maps.newHashMap();
        map.put("EQ_tid", 0L);
        List<OrderDomain> orderDomains = orderService.list(map);

        /*Optional<OrderDomain> domain = Optional.ofNullable(orderService.get(251478030793310210L, 0L));
        System.out.println(domain.isPresent());
        if(domain.isPresent()) {
            System.out.println(domain.get().toString());
        }*/
        return result;
    }

}
