package com.sword.module.sj;

import com.google.common.collect.Maps;
import com.sword.module.ds.DataSourceAutoConfig;
import io.shardingsphere.core.api.ShardingDataSourceFactory;
import io.shardingsphere.core.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.api.config.TableRuleConfiguration;
import io.shardingsphere.core.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingsphere.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.core.api.config.strategy.ShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableConfigurationProperties({ShardingJdbcProperties.class})
@AutoConfigureAfter(DataSourceAutoConfig.class)
@ConditionalOnProperty(value = "sword.sj.enable", matchIfMissing = true)
public class ShardingJdbcAutoConfig {

    @Resource
    private ShardingJdbcProperties shardingJdbcProperties;

    @Bean
    public ShardingRuleConfiguration shardingRuleConfiguration(CustomComplexKeysShardingAlgorithm myPreciseShardingAlgorithm) {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.setDefaultDataSourceName(shardingJdbcProperties.getDefaultDataSourceName());
        ShardingStrategyConfiguration shardingStrategyConfiguration = new InlineShardingStrategyConfiguration(
                shardingJdbcProperties.getShardingColumName(),
                shardingJdbcProperties.getDefaultDataSourceName());
        if(!Objects.isNull(shardingJdbcProperties.getShardingTables())) {
            List<TableRuleConfiguration> tableRuleConfigurations = Arrays.stream(shardingJdbcProperties.getShardingTables())
                    .map(this::createTableRuleCfg).collect(Collectors.toList());
            shardingRuleConfiguration.setTableRuleConfigs(tableRuleConfigurations);
        }
        ShardingStrategyConfiguration var1 = new ComplexShardingStrategyConfiguration(shardingJdbcProperties.getShardingColumName(),
                myPreciseShardingAlgorithm);
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(var1);
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(shardingStrategyConfiguration);
        return shardingRuleConfiguration;
    }

    @Bean
    public CustomComplexKeysShardingAlgorithm myPreciseShardingAlgorithm() {
        return new CustomComplexKeysShardingAlgorithm();
    }

    @Bean
    public DataSource sjDataSource(DataSource dataSource, ShardingRuleConfiguration shardingRuleConfiguration) {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(shardingJdbcProperties.getDefaultDataSourceName(), dataSource);
        Map<String, Object> configMap = Maps.newHashMap();
        Properties properties = new Properties();
        try {
            return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, configMap, properties);
        } catch (SQLException e) {
            log.error("创建sharding数据源失败：{}", e);
        }
        return dataSource;
    }

    public TableRuleConfiguration createTableRuleCfg(String table) {
        // 数据库表
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
        StringBuffer actualDataNodes = new StringBuffer().append(shardingJdbcProperties.getDefaultDataSourceName())
                .append(".").append(table).append("_${0..1}");
        StringBuffer algorithmExpression = new StringBuffer().append(table).append("_${")
                .append(shardingJdbcProperties.getShardingColumName()).append("}");
        tableRuleConfiguration.setActualDataNodes(actualDataNodes.toString());
        ShardingStrategyConfiguration var1 = new InlineShardingStrategyConfiguration(shardingJdbcProperties.getShardingColumName(),
                algorithmExpression.toString());
        tableRuleConfiguration.setTableShardingStrategyConfig(var1);
        tableRuleConfiguration.setKeyGenerator(new DefaultKeyGenerator());
        tableRuleConfiguration.setKeyGeneratorColumnName(shardingJdbcProperties.getGeneratorColumnName());
        tableRuleConfiguration.setLogicTable(table);
        return tableRuleConfiguration;
    }

}
