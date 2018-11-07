package com.sword.cloud.auth.service.impl;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    @Override
    protected void store(String s, OAuth2Authentication oAuth2Authentication) {

    }

    @Override
    protected OAuth2Authentication remove(String s) {
        return null;
    }
}
