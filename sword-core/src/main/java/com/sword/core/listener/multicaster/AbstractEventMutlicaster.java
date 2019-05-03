package com.sword.core.listener.multicaster;

import com.google.common.collect.Lists;
import com.sword.core.listener.BaseEventMulticaster;
import com.sword.core.listener.BaseListener;
import com.sword.core.listener.SmartListener;

import java.util.List;

public abstract class AbstractEventMutlicaster<E> implements BaseEventMulticaster<E> {

    private List<BaseListener<E>> listeners = Lists.newCopyOnWriteArrayList();

    @Override
    public void addListener(BaseListener<E> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(BaseListener<E> listener) {

    }

    @Override
    public void pulisheEvent(final E event) {
        listeners.forEach(v -> {
            boolean support = ((SmartListener) v).supportsEventType(event.getClass());
            if(support) {
                v.onEvent(event);
            }
        });
    }
}
