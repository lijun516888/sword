package com.sword.module.mybatis.common.simple;

import com.sword.core.dto.PageInfo;
import com.sword.module.mybatis.common.BaseEntityService;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class EntityServiceImpl<T, M extends EntityMybatisDao<T>> extends BaseEntityService<M> implements ApplicationContextAware, EntityService<T> {

    @Override
    public T get(Serializable id) {
        return this.getEntityDao().get(id);
    }

    public List<T> getAll() throws Exception {
        return this.getEntityDao().getAll();
    }

    public void remove(T o) throws Exception {
        this.getEntityDao().remove(o);
    }

    public void removeById(Serializable id) throws Exception {
        this.getEntityDao().removeById(id);
    }

    public void removes(Serializable... ids) throws Exception {
        this.getEntityDao().removes(ids);
    }

    public void save(T o) throws Exception {
        this.getEntityDao().create(o);
    }

    public void update(T o) throws Exception {
        this.getEntityDao().update(o);
    }

    public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap) throws Exception {
        return this.getEntityDao().query(pageInfo, map, orderMap);
    }

    public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map) throws Exception {
        return this.query(pageInfo, map, (Map)null);
    }

    public List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap) {
        return this.getEntityDao().list(map, sortMap);
    }
}
