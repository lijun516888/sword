package com.sword.cloud.user.service.impl;

import com.sword.cloud.model.user.LoginAppUser;
import com.sword.cloud.user.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public LoginAppUser findByUsername(String username) {
        LoginAppUser user = new LoginAppUser();
        user.setId(1L);
        return user;
    }
}
