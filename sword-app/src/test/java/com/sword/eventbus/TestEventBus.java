package com.sword.eventbus;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

public class TestEventBus {

    @Test
    public void test() {
        EventBus eventBus = new EventBus();
        eventBus.register(new MyEvent());
        eventBus.post("12345");
    }

}
