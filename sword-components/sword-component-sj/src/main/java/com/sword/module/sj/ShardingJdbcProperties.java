package com.sword.module.sj;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "sword.sj")
public class ShardingJdbcProperties {

    private boolean eanble = true;
    private String[] shardingTables;
    private String[] dataSourceNames;
    private String defaultDataSourceName;
    private String shardingColumName;
    private String generatorColumnName="ID";
}
