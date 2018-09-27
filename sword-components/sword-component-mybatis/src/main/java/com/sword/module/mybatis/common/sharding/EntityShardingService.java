package com.sword.module.mybatis.common.sharding;

import com.sword.core.dto.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityShardingService<T> {

    void save(T domain);

    T get(Serializable id, Serializable tid);

    List<T> list(Map<String, Object> map);

    PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map);

}
