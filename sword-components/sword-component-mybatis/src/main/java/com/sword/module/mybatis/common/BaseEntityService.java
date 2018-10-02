package com.sword.module.mybatis.common;

import com.sword.core.utils.GenericsUtils;
import com.sword.core.utils.bean.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.util.List;

public class BaseEntityService<M extends EntityDao> implements ApplicationContextAware {

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
}
