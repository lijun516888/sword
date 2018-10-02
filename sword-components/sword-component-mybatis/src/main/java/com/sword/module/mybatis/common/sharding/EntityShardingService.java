package com.sword.module.mybatis.common.sharding;

import com.sword.core.dto.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityShardingService<T> {

    T get(Serializable id, Serializable tid) throws Exception;

    List<T> getAll(Long tid) throws Exception;

    void save(T t) throws Exception;

    void update(T t) throws Exception;

    void remove(T t) throws Exception;

    void removeById(Serializable id, Serializable tid) throws Exception;

    void removes(Serializable tid, Serializable... ids) throws Exception;

    PageInfo<T> query(Serializable tid, PageInfo<T> pageInfo, Map<String, Object> map, Map<String, Boolean> sortMap) throws Exception;

    PageInfo<T> query(Serializable tid, PageInfo<T> pageInfo, Map<String, Object> map) throws Exception;

    List<T> query(Serializable tid, Map<String, Object> map, Map<String, Boolean> sortMap);

}
