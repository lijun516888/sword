package com.sword.core.lesson0425.listener;

import com.sword.core.lesson0425.AbstractListener;
import com.sword.core.lesson0425.event.TvEvent;

public class TvListener extends AbstractListener<TvEvent> {

    @Override
    public void onEvent(TvEvent event) {
        System.out.println("*******************" + event.getName());
    }
}
