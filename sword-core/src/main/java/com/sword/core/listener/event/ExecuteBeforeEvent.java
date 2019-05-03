package com.sword.core.listener.event;

import lombok.Data;

@Data
public class ExecuteBeforeEvent extends BaseEvent {
    private String name;
    public ExecuteBeforeEvent(String name) {
        this.name = name;
    }
}
