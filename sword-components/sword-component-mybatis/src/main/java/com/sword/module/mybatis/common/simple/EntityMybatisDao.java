package com.sword.module.mybatis.common.simple;

import com.sword.module.mybatis.common.EntityDao;
import com.sword.module.mybatis.mapper.GetMapper;

public interface EntityMybatisDao<T> extends EntityDao<T>, GetMapper<T> {

}
