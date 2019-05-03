package com.sword.core.lesson0425.test;

import com.sword.core.lesson0425.SupperListener;
import com.sword.core.lesson0425.event.TvEvent;
import com.sword.core.lesson0425.listener.TvListener;
import com.sword.core.lesson0425.mutlicaster.SystemMulticaster;

public class PublisherTest {

    private SystemMulticaster systemMulticaster = new SystemMulticaster();

    public static void main(String[] args) {
        PublisherTest test = new PublisherTest();
        TvListener tvListener = new TvListener();
        test.systemMulticaster.addListener((SupperListener) tvListener);

        test.systemMulticaster.publisheEvent(new TvEvent("张三"));
    }

}
