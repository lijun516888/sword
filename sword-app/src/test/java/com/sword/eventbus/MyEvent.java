package com.sword.eventbus;

import com.google.common.eventbus.Subscribe;

public class MyEvent {

    @Subscribe
    public void sub(String message) {
        System.out.println("sub" +message);
    }

    @Subscribe
    public void sub1(Integer message) {
        System.out.println("sub1" + message);
    }
}
