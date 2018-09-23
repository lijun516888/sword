package com.sword.module.ds;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
@ConditionalOnProperty(value = "sword.ds.enable", matchIfMissing = true)
public class DataSourceAutoConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassLoader(ClassUtils.getDefaultClassLoader());
        dataSource.setUrl("jdbc:mysql://localhost:3306/amooly?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("12qwaszx!");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(20);
        dataSource.setMaxActive(300);
        dataSource.setMaxWait(10000);
        dataSource.setTimeBetweenEvictionRunsMillis(300000L);
        dataSource.setMinEvictableIdleTimeMillis(3600000L);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setValidationQueryTimeout(5);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(3600);
        dataSource.setLogAbandoned(true);
        dataSource.setValidationQuery("select 'x'");
        dataSource.setValidationQueryTimeout(2);
        Properties properties = new Properties();
        properties.put("druid.stat.slowSqlMillis", "0");
        properties.put("druid.stat.logSlowSql", Boolean.TRUE.toString());
        dataSource.setConnectProperties(properties);
        try {
            dataSource.setFilters("stat");
            dataSource.init();
        } catch (SQLException var4) {
            throw new RuntimeException("druid连接池初始化失败", var4);
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
