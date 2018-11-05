package com.sword.cloud.user.service;

import com.sword.cloud.user.domain.UserDomain;

public interface UserService {

    UserDomain findByUsername(String username);

}
