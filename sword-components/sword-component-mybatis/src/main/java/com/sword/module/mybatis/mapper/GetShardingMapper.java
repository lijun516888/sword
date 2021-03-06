package com.sword.module.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.provider.base.BaseSelectProvider;

import java.io.Serializable;

public interface GetShardingMapper<T> {

    @SelectProvider(
            type = GetShardingMapper.GetProvider.class,
            method = "dynamicSQL"
    )
    T get(@Param("id") Serializable id, @Param("tid") Serializable tid);

    class GetProvider extends BaseSelectProvider {
        public GetProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }
        public String get(MappedStatement ms) {
            return super.selectByPrimaryKey(ms);
        }

    }
}
