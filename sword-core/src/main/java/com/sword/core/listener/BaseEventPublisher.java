package com.sword.core.listener;

public interface BaseEventPublisher<E> {
    void pulisheEvent(E event);
}
