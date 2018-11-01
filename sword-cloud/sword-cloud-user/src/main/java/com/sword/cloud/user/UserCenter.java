package com.sword.cloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UserCenter {

    public static void main(String[] args) {
        SpringApplication.run(UserCenter.class, args);
    }

}
