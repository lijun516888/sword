package com.sword.cloud.user.service;

import com.sword.cloud.model.user.LoginAppUser;

public interface UserService {

    LoginAppUser findByUsername(String username);

}
