package com.javarush.island.pukhov.services;

import com.javarush.island.pukhov.entity.IslandApplication;
import com.javarush.island.pukhov.entity.IslandProcessor;
import com.javarush.island.pukhov.exception.ErrorHandler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainService implements Runnable {

    private final ExecutorService executor;
    private final List<Runnable> services;
    private final ErrorHandler errorHandler;
    private final IslandApplication application;
    private final IslandProcessor mainProcessor;

    public MainService(IslandApplication application, IslandProcessor islandProcessor, int corePoolSize) {
        executor = Executors.newWorkStealingPool(corePoolSize);
        this.services = application.getServices();
        this.errorHandler = application.getErrorHandler();
        this.application = application;
        mainProcessor = islandProcessor;
    }

    @Override
    public void run() {
        try {
            if (application.isFinished()) {
                stopService();
            } else {
                services.forEach(executor::execute);
            }
        } catch (RuntimeException e) {
            errorHandler.handle(e);
            stopService();
        }
    }

    private void stopService() {
        executor.shutdown();
        mainProcessor.stop();
    }
}
