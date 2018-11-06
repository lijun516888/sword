package com.sword.cloud.user.service;

import com.sword.cloud.model.UserDomain;

public interface UserService {

    UserDomain findByUsername(String username);

}
