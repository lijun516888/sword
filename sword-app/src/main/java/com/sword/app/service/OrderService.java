package com.sword.app.service;

import com.sword.app.domain.OrderDomain;
import com.sword.module.mybatis.common.sharding.EntityShardingService;

public interface OrderService extends EntityShardingService<OrderDomain> {
}
