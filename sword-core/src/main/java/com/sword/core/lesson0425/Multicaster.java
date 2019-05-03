package com.sword.core.lesson0425;

public interface Multicaster<E> extends SupperPublisher<E> {
    void addListener(SupperListener<E> listener);
    void removeListener(SupperListener<E> listener);
}
