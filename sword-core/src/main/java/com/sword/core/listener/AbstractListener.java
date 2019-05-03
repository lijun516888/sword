package com.sword.core.listener;

import com.sword.core.listener.event.BaseEvent;
import com.sword.core.listener.testlistener.ReflectTest;

public abstract class AbstractListener<E extends BaseEvent> implements SmartListener<E> {

    private Class<E> clazz;

    public AbstractListener() {
        clazz = ReflectTest.getSuperClassGenricType(this.getClass());
    }

    @Override
    public boolean supportsEventType(Class<E> eventType) {
        return this.clazz.isAssignableFrom(eventType);
    }

}
