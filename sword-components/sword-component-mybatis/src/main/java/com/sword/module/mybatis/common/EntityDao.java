package com.sword.module.mybatis.common;

import com.sword.module.mybatis.mapper.CreateMapper;
import com.sword.module.mybatis.mapper.ListMapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Marker;

public interface EntityDao<T> extends BaseMapper<T>, ExampleMapper<T>, Marker, CreateMapper<T>, ListMapper<T> {

}
