package com.sword.core.listener;

public interface BaseEventMulticaster<E> extends BaseEventPublisher<E> {

    void addListener(BaseListener<E> listener);

    void removeListener(BaseListener<E> listener);

}
