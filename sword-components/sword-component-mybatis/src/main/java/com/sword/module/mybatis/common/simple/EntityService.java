package com.sword.module.mybatis.common.simple;

import com.sword.core.dto.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityService<T> {

    T get(Serializable id);

    List<T> getAll() throws Exception;

    void save(T t) throws Exception;

    void update(T t) throws Exception;

    void remove(T t) throws Exception;

    void removeById(Serializable id) throws Exception;

    void removes(Serializable... ids) throws Exception;

    PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map, Map<String, Boolean> sortMap) throws Exception;

    PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map) throws Exception;

    List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap);

}
