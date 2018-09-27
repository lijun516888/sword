package com.sword.module.mybatis;

import com.github.pagehelper.autoconfigure.MapperProperties;
import com.sword.module.mybatis.common.sharding.EntityMybatisShardingDao;
import com.sword.module.mybatis.common.simple.EntityMybatisDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Configuration
@EnableConfigurationProperties({MapperProperties.class})
@AutoConfigureAfter(MybatisAutoConfig.class)
public class MapperAutoConfig {

    private final List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    private MapperProperties mapperProperties;

    @Autowired
    private ApplicationContext applicationContext;

    public MapperAutoConfig(List<SqlSessionFactory> sqlSessionFactoryList) {
        this.sqlSessionFactoryList = sqlSessionFactoryList;
    }

    /**
     * 没太搞懂，这个配置的作用是什么。从代码来看就是管理mapper的，
     * 此处第一行代码是我固定写死的，不写的话启动会报错，找不到
     * 对就的mapper类。还没找到方法让它自动注入进去。
     * springboot 1.5中mapperProperties会自动注入值
     * 但是2.0的却没有自动注入值
     * 原因是：ConfigFileApplicationListener类中方法postProcessEnvironment与1.5的不一样
     */
    @PostConstruct
    public void addPageInterceptor() {
        mapperProperties.setMappers(Arrays.asList(EntityMybatisDao.class, EntityMybatisShardingDao.class));
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(this.mapperProperties);
        Iterator var2;
        if (this.mapperProperties.getMappers().size() > 0) {
            var2 = this.mapperProperties.getMappers().iterator();
            while(var2.hasNext()) {
                Class mapper = (Class)var2.next();
                this.applicationContext.getBeansOfType(mapper);
                mapperHelper.registerMapper(mapper);
            }
        } else {
            this.applicationContext.getBeansOfType(Mapper.class);
            mapperHelper.registerMapper(Mapper.class);
        }
        var2 = this.sqlSessionFactoryList.iterator();
        while(var2.hasNext()) {
            SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)var2.next();
            mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
        }
    }
}