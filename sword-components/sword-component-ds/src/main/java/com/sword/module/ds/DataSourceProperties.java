package com.sword.module.ds;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sword.ds")
public class DataSourceProperties {
    private String url;
    private String username;
    private String password;
    private boolean enable = true;
}
