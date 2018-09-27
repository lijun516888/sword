package com.sword.module.mybatis.common.simple;

import com.sword.core.dto.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityService<T> {

    T get(Serializable id);

    List<T> list(Map<String, Object> map);

    PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map);

    void save(T domain);

}
