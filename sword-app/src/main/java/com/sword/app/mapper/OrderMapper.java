package com.sword.app.mapper;

import com.sword.app.domain.OrderDomain;
import com.sword.module.mybatis.common.sharding.EntityMybatisShardingDao;

public interface OrderMapper extends EntityMybatisShardingDao<OrderDomain> {

}
