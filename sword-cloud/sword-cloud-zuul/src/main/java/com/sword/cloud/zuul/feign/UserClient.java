package com.sword.cloud.auth.feign;

import com.sword.cloud.model.UserDomain;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-center")
public interface UserClient {

    @GetMapping(value = "/users-anon/internal", params = "username")
    UserDomain findByUsername(@RequestParam("username") String username);

}
