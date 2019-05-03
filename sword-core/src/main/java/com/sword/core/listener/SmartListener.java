package com.sword.core.listener;

import com.sword.core.listener.event.BaseEvent;

public interface SmartListener<E extends BaseEvent> extends  BaseListener<E>  {

    boolean supportsEventType(Class<E> eventType);
}
