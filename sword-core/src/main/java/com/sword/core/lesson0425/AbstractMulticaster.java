package com.sword.core.lesson0425;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class AbstractMulticaster<E> implements Multicaster<E> {

    List<SupperListener<E>> listeners = Lists.newCopyOnWriteArrayList();

    @Override
    public void addListener(SupperListener<E> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(SupperListener<E> listener) {
        listeners.remove(listener);
    }

    @Override
    public void publisheEvent(E event) {
        listeners.forEach(v -> {
            if(((SmartListener) v).supportsEvent(event.getClass())) {
                v.onEvent(event);
            }
        });
    }
}
