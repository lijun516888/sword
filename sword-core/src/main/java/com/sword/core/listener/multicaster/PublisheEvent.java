package com.sword.core.listener.multicaster;

import com.sword.core.listener.BaseListener;
import com.sword.core.listener.event.BaseEvent;
import com.sword.core.listener.event.ExecuteAfterEvent;
import com.sword.core.listener.event.ExecuteBeforeEvent;
import com.sword.core.listener.testlistener.CustomeListener;

public class PublisheEvent {

    private SystemEventMutlicaster systemEventMutlicaster = new SystemEventMutlicaster();

    public static void main(String[] args) {
        PublisheEvent publisheEvent = new PublisheEvent();
        CustomeListener customeListener = new CustomeListener();
        // 添加所有监听者
        publisheEvent.systemEventMutlicaster.addListener((BaseListener) customeListener);
        // 发送事件
        publisheEvent.publisheEvent(new ExecuteBeforeEvent("李军"));
        publisheEvent.publisheEvent(new ExecuteAfterEvent("张"));
    }

    public void publisheEvent(BaseEvent event) {
        systemEventMutlicaster.pulisheEvent(event);
    }

}
