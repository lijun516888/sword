package com.sword.module.sj;

import com.google.common.collect.Maps;
import com.sword.module.ds.DataSourceAutoConfig;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Configuration
@EnableConfigurationProperties({ShardingJdbcProperties.class})
@AutoConfigureAfter(DataSourceAutoConfig.class)
@ConditionalOnProperty(value = "sword.sj.enable", matchIfMissing = true)
public class ShardingJdbcAutoConfig {

    @Bean
    public ShardingRuleConfiguration shardingRuleConfiguration() {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        return shardingRuleConfiguration;
    }

    @Bean
    public DataSource sjDataSource(DataSource dataSource, ShardingRuleConfiguration shardingRuleConfiguration) {
        // Map<String, DataSource> dataSourceMap, ShardingRuleConfiguration shardingRuleConfig, Map<String, Object> configMap, Properties props
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        Map<String, Object> configMap = Maps.newHashMap();
        Properties properties = new Properties();
        try {
            return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, configMap, properties);
        } catch (SQLException e) {
            log.error("创建sharding数据源失败：{}", e);
        }
        return dataSource;
    }
}
