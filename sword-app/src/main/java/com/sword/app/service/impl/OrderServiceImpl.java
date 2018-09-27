package com.sword.app.service.impl;

import com.sword.app.domain.OrderDomain;
import com.sword.app.mapper.OrderMapper;
import com.sword.app.service.OrderService;
import com.sword.module.mybatis.common.sharding.EntityServiceShardingImpl;
import org.springframework.stereotype.Service;

@Service("orderService")
public class OrderServiceImpl extends EntityServiceShardingImpl<OrderDomain, OrderMapper> implements OrderService {

}
