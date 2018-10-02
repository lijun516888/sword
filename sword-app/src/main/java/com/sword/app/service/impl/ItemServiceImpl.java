package com.sword.app.service.impl;

import com.sword.app.domain.ItemDomain;
import com.sword.app.mapper.ItemMapper;
import com.sword.app.service.ItemService;
import com.sword.module.mybatis.common.simple.EntityServiceImpl;
import org.springframework.stereotype.Service;

@Service("itemService")
public class ItemServiceImpl extends EntityServiceImpl<ItemDomain, ItemMapper> implements ItemService {

}
