package com.sword.core.listener.event;

import lombok.Data;

@Data
public class ExecuteAfterEvent extends BaseEvent {
    private String name;
    public ExecuteAfterEvent(String name) {
        this.name = name;
    }
}
