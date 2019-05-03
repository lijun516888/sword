package com.sword.core.listener.testlistener;

import com.sword.core.listener.AbstractListener;
import com.sword.core.listener.event.BaseEvent;
import com.sword.core.listener.event.ExecuteAfterEvent;
import com.sword.core.listener.event.ExecuteBeforeEvent;

public class CustomeListener extends AbstractListener<BaseEvent> {

    @Override
    public void onEvent(BaseEvent event) {
        if(event instanceof ExecuteBeforeEvent) {
            System.out.println(((ExecuteBeforeEvent) event).getName());
        } else if(event instanceof ExecuteAfterEvent) {
            System.out.println(((ExecuteAfterEvent) event).getName());
        }
        System.out.println(event.getClass().getName());
    }
}
