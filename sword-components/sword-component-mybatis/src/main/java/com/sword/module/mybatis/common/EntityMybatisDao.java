package com.sword.module.mybatis.common;

import com.sword.module.mybatis.mapper.CreateMapper;
import com.sword.module.mybatis.mapper.GetMapper;
import com.sword.module.mybatis.mapper.ListMapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Marker;

public interface EntityMybatisDao<T> extends BaseMapper<T>, ExampleMapper<T>, Marker, CreateMapper<T>, GetMapper<T>, ListMapper<T> {

}
