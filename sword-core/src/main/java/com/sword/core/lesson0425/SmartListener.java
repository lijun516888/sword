package com.sword.core.lesson0425;

import com.sword.core.lesson0425.event.SupperEvent;

public interface SmartListener<E extends SupperEvent> extends SupperListener<E> {
    boolean supportsEvent(Class<E> eventType);
}
