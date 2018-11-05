package com.sword.cloud.user.service.impl;

import com.sword.cloud.user.domain.UserDomain;
import com.sword.cloud.user.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Override
    public UserDomain findByUsername(String username) {
        UserDomain user = new UserDomain();
        user.setId(1L);
        user.setName("测试用户");
        user.setAge(28);
        return user;
    }
}
