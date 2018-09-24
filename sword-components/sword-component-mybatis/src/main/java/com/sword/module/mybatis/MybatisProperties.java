package com.sword.module.mybatis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sword.mybatis")
public class MybatisProperties {

    private boolean enable = true;
}
