package com.javarush.island.pukhov.entity;

import com.javarush.island.pukhov.api.life.ShutdownListener;
import com.javarush.island.pukhov.concurrent.Period;
import com.javarush.island.pukhov.services.ManagerService;

import java.util.concurrent.ScheduledExecutorService;

public class IslandProcessor implements ShutdownListener {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final IslandApplication application;
    private final ScheduledExecutorService executorService;
    private final ManagerService managerService;
    private final Period period;

    public IslandProcessor(IslandApplication application, Period period, ScheduledExecutorService executorService) {
        this.application = application;
        this.executorService = executorService;
        managerService = new ManagerService(application,this, CORE_POOL_SIZE);
        this.period = period;
    }

    public void start() {
        executorService.scheduleAtFixedRate(managerService,
                period.getDelay(),
                period.getPeriodTime(),
                period.getTimeUnit());
    }

    @Override
    public void onShutdown() {
        stop();
    }

    public void stop() {
        managerService.shutdown();
        executorService.shutdown();
        application.getView().showFinish();
    }

}
