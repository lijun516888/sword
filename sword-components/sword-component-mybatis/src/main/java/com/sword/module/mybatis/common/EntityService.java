package com.sword.module.mybatis.common;

import com.sword.core.dto.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface EntityService<T> {

    T get(Serializable id);

    T getTid(Serializable id, Long tid);

    List<T> list(Map<String, Object> map);

    PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map);

}
