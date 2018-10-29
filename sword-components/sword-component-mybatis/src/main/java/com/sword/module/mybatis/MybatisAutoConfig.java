package com.sword.module.mybatis;

import com.sword.module.mybatis.interceptor.PageExecutorInterceptor;
import com.sword.module.mybatis.page.PageObjectFactory;
import com.sword.module.mybatis.page.PageObjectWrapperFactory;
import com.sword.module.sj.ShardingJdbcAutoConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;

@Configuration
@Import(MapperScannerRegistrar.class)
@EnableConfigurationProperties({MybatisProperties.class})
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(ShardingJdbcAutoConfig.class)
public class MybatisAutoConfig {

    @Bean
    public Interceptor[] interceptors() {
        Interceptor[] interceptors = new Interceptor[1];
        interceptors[0] = new PageExecutorInterceptor();
        return interceptors;
    }

    @ConditionalOnBean(DataSource.class)
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource sjDataSource, Interceptor[] interceptors) {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(sjDataSource);
        /**
         * 在Spring Boot中，由于是嵌套Jar，导致Mybatis默认的VFS实现DefaultVFS无法扫描嵌套Jar中的类。
         * 解决办法，实现自定义的VFS，参考DefaultVFS增加对Spring Boot嵌套JAR的处理。
         */
        factory.setVfs(SpringBootVFS.class);
        if (!ObjectUtils.isEmpty(interceptors)) {
            factory.setPlugins(interceptors);
        }
        SqlSessionFactory sqlSessionFactory;
        try {
            sqlSessionFactory = factory.getObject();
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
        this.customConfig(sqlSessionFactory.getConfiguration());
        return sqlSessionFactory;
    }

    private void customConfig(org.apache.ibatis.session.Configuration configuration) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(configuration);
        configuration.setObjectFactory(new PageObjectFactory());
        configuration.setObjectWrapperFactory(new PageObjectWrapperFactory());
    }

}
