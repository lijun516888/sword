package com.sword.module.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.provider.base.BaseInsertProvider;

public interface CreateMapper<T> {

    @InsertProvider(
            type = CreateMapper.CreateProvider.class,
            method = "dynamicSQL"
    )
    void create(T o);

    class CreateProvider extends BaseInsertProvider {

        public CreateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }
        public String create(MappedStatement ms) {
            return super.insert(ms);
        }
    }
}
