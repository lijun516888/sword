package com.sword.module.mybatis.common.sharding;

import com.sword.core.dto.PageInfo;
import com.sword.module.mybatis.common.BaseEntityService;
import com.sword.module.mybatis.common.domain.BaseDomain;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class EntityServiceShardingImpl<T, M extends EntityMybatisShardingDao<T>> extends BaseEntityService<M> implements EntityShardingService<T> {

    public T get(Serializable id, Serializable tid) throws Exception {
        return this.getEntityDao().get(id, tid);
    }

    public List<T> getAll(Long tid) throws Exception {
        return this.getEntityDao().getShardingAll("EQ_tid", tid);
    }

    public void remove(T o) throws Exception {
        this.getEntityDao().remove(o);
    }

    public void removeById(Serializable id, Serializable tid) throws Exception {
        this.getEntityDao().removeShardingById(id, tid);
    }

    public void removes(Serializable tid, Serializable... ids) throws Exception {
        this.getEntityDao().removesSharding(tid, ids);
    }

    public void save(T o) throws Exception {
        this.getEntityDao().create(o);
    }

    public void update(T o) throws Exception {
        this.getEntityDao().update(o);
    }

    public void saveOrUpdate(T t) throws Exception {
        if (t instanceof BaseDomain) {
            if (((BaseDomain)t).getId() != null) {
                this.getEntityDao().update(t);
            } else {
                this.getEntityDao().create(t);
            }

        } else {
            throw new UnsupportedOperationException("实体类必须继承BaseDomain才能使用saveOrUpdate方法");
        }
    }

    public PageInfo<T> query(Serializable tid, PageInfo<T> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap) throws Exception {
        map.put("EQ_tid", tid);
        return this.getEntityDao().query(pageInfo, map, orderMap);
    }

    public PageInfo<T> query(Serializable tid, PageInfo<T> pageInfo, Map<String, Object> map) throws Exception {
        return this.query(tid, pageInfo, map, (Map)null);
    }

    public List<T> query(Serializable tid, Map<String, Object> map, Map<String, Boolean> sortMap) {
        map.put("EQ_tid", tid);
        return this.getEntityDao().list(map, sortMap);
    }

}
