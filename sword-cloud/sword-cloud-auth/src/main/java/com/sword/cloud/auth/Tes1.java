package com.sword.cloud.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Tes1 {

    public static void main(String[] args) {
        BCryptPasswordEncoder d = new BCryptPasswordEncoder();
        System.out.println(d.encode("admin"));
    }

}
