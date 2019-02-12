package com.sword.eventbus;

import com.google.common.eventbus.EventBus;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestEventBus {

    @Test
    public void test() {
        EventBus eventBus = new EventBus();
        eventBus.register(new MyEvent());
        eventBus.post("12345");
    }

    @Test
    public void test1() {
        System.out.println(StringUtils.isBlank(null));
    }

}
