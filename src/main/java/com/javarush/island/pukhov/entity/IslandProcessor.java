package com.javarush.island.pukhov.entity;

import com.javarush.island.pukhov.concurrent.Period;
import com.javarush.island.pukhov.services.MainService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class IslandProcessor {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final IslandApplication application;
    private final ScheduledExecutorService executorService;
    private final MainService mainService;
    private final Period period;

    public IslandProcessor(IslandApplication application, Period period) {
        this.application = application;
        executorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        mainService = new MainService(application,this, CORE_POOL_SIZE);
        this.period = period;
    }

    public void start() {
        executorService.scheduleAtFixedRate(mainService,
                period.getDelay(),
                period.getPeriodTime(),
                period.getTimeUnit());
    }

    public void stop() {
        executorService.shutdown();
        application.getView().showFinish();
    }
}
