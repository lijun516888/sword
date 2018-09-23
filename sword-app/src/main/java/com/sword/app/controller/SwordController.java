package com.sword.app.controller;

import com.sword.core.dto.JsonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sword")
public class SwordController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @RequestMapping(value = "user")
    public JsonEntity<String> doGetUser() {
        JsonEntity<String> result = new JsonEntity<>();
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("SELECT * FROM SYS_USER");
        result.setEntity(mapList.get(0).get("PASSWORD").toString());
        return result;
    }

}
