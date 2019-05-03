package com.sword.core.lesson0425.event;

public class TvEvent extends SupperEvent {

    private String name;

    public TvEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
