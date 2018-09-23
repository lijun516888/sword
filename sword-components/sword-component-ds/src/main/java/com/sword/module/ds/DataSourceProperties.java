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
    private String username = "root";
    private String password = "111111";
    private boolean enable = true;
    private String slowSqlMillis = "2";
    private boolean logSlowSql = true;

    public String getUrl() {
        return this.url.contains("?") ? url : url + "?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false";
    }
}
