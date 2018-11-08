package com.sword.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableZuulProxy
@SpringBootApplication
public class ZuulCenter {

    public static void main(String[] args) {
        SpringApplication.run(ZuulCenter.class, args);
    }

}
