package com.sword.module.sj;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sword.module.ds.DataSourceAutoConfig;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.core.api.config.strategy.ShardingStrategyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
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
        // 数据库
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.setDefaultDataSourceName("ds_0");
        ShardingStrategyConfiguration shardingStrategyConfiguration = new InlineShardingStrategyConfiguration("tid","ds_0");
        // 数据库表
        TableRuleConfiguration tableRuleConfiguration1 = new TableRuleConfiguration();
        tableRuleConfiguration1.setActualDataNodes("ds_0.t_order_${0..1}");

        ShardingStrategyConfiguration var1 = new InlineShardingStrategyConfiguration("tid",
                "t_order_${tid % 2}");
        tableRuleConfiguration1.setTableShardingStrategyConfig(var1);
        tableRuleConfiguration1.setKeyGeneratorColumnName("id");
        tableRuleConfiguration1.setLogicTable("t_order");

        List<TableRuleConfiguration> tableRuleConfigurations = Lists.newArrayList();
        tableRuleConfigurations.add(tableRuleConfiguration1);
        // 配置分表
        shardingRuleConfiguration.setTableRuleConfigs(tableRuleConfigurations);
        // 配置分库
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(shardingStrategyConfiguration);
        return shardingRuleConfiguration;
    }

    @Bean
    public DataSource sjDataSource(DataSource dataSource, ShardingRuleConfiguration shardingRuleConfiguration) {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put("ds_0", dataSource);
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
