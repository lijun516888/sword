package com.sword.cloud.user.service.impl;

import com.sword.cloud.model.user.LoginAppUser;
import com.sword.cloud.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public LoginAppUser findByUsername(String username) {
        LoginAppUser user = new LoginAppUser();
        user.setId(1L);
        user.setCreateTime(new Date());
        user.setEnabled(true);
        user.setHeadImgUrl("");
        user.setNickname("");
        user.setPassword("111111");
        user.setPhone("13132354113");
        user.setSex(1);
        user.setUpdateTime(new Date());
        user.setUsername("admin");
        user.setType("admin");
        return user;
    }
}
