package com.sword.module.mybatis.common;

import com.sword.core.dto.PageInfo;
import com.sword.core.utils.GenericsUtils;
import com.sword.core.utils.bean.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class EntityServiceImpl<T, M extends EntityMybatisDao<T>> implements ApplicationContextAware, EntityService<T> {

    private M entityDao;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    protected M getEntityDao() {
        if(this.entityDao != null) {
            return this.entityDao;
        } else {
            Class<M> daoType = GenericsUtils.getSuperClassGenricType(this.getClass(), 1);
            List fields = BeanUtils.getFieldsByType(this, daoType);
            try {
                if (fields != null && fields.size() > 0) {
                    this.entityDao = (M) BeanUtils.getDeclaredProperty(this, ((Field)fields.get(0)).getName());
                } else {
                    this.entityDao = (M) this.context.getBean(daoType);
                }
            } catch (Exception var4) {
                throw new RuntimeException(var4);
            }
            return this.entityDao;
        }
    }

    @Override
    public T get(Serializable id) {
        return null;
    }

    @Override
    public PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map) {
        return this.getEntityDao().query(pageInfo, map, null);
    }

    @Override
    public List<T> list(Map<String, Object> map) {
        return this.getEntityDao().list(map, null);
    }
}
