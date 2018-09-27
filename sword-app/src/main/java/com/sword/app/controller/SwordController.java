package com.sword.app.controller;

import com.google.common.collect.Maps;
import com.sword.app.domain.OrderDomain;
import com.sword.app.domain.UserDomain;
import com.sword.app.mapper.OrderMapper;
import com.sword.app.service.UserService;
import com.sword.core.dto.JsonEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/sword")
public class SwordController {

    @Resource
    private UserService userService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private DataSource dataSource;

    @ResponseBody
    @RequestMapping(value = "user")
    public JsonEntity<UserDomain> doGetUser() {
        JsonEntity<UserDomain> result = new JsonEntity<>();
        Map<String, Object> map = Maps.newHashMap();
        List<UserDomain> userDomains = userService.list(map);
        result.setEntity(userDomains.get(0));

        OrderDomain var = new OrderDomain();
        var.setName("B");
        var.setTid(1);
        orderMapper.create(var);

        Optional<OrderDomain> domain = Optional.ofNullable(orderMapper.get(251478030793310210L));
        System.out.println(domain.isPresent());
        if(domain.isPresent()) {
            System.out.println(domain.get().toString());
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from t_order_1 where id = " +
                "'251478030793310210'");

        return result;
    }

}
