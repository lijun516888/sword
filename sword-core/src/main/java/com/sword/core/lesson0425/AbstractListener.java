package com.sword.core.lesson0425;

import com.sword.core.lesson0425.event.SupperEvent;
import com.sword.core.utils.GenericsUtils;

public abstract class AbstractListener<E extends SupperEvent> implements SmartListener<E> {

    private Class<E> clazz;

    public AbstractListener() {
        clazz = GenericsUtils.getSuperClassGenricType(this.getClass(), 0);
    }

    @Override
    public boolean supportsEvent(Class<E> eventType) {
        return clazz.isAssignableFrom(eventType);
    }
}
