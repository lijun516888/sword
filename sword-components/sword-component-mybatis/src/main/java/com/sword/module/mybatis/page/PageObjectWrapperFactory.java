package com.sword.module.mybatis.page;

import com.sword.core.dto.PageInfo;
import lombok.NoArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

@NoArgsConstructor
public class PageObjectWrapperFactory extends DefaultObjectWrapperFactory {

    public boolean hasWrapperFor(Object object) {
        return PageInfo.class.isAssignableFrom(object.getClass());
    }

    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if (PageInfo.class.isAssignableFrom(object.getClass())) {
            return new PageObjectWrapper((PageInfo)object);
        } else {
            throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
        }
    }
}
