package com.sword.app.service.impl;

import com.sword.app.domain.UserDomain;
import com.sword.app.mapper.UserMapper;
import com.sword.app.service.UserService;
import com.sword.module.mybatis.common.simple.EntityServiceImpl;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends EntityServiceImpl<UserDomain, UserMapper> implements UserService {

}
