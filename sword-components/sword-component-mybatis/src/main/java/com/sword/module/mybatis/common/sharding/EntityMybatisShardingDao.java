package com.sword.module.mybatis.common.sharding;

import com.sword.module.mybatis.common.EntityDao;
import com.sword.module.mybatis.mapper.GetShardingMapper;

public interface EntityMybatisShardingDao<T> extends EntityDao<T>, GetShardingMapper<T> {

}
