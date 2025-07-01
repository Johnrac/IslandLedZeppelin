package com.javarush.island.pukhov.services;

import com.javarush.island.pukhov.api.life.ShutdownListener;
import com.javarush.island.pukhov.entity.IslandApplication;
import com.javarush.island.pukhov.exception.ErrorHandler;
import com.javarush.island.pukhov.exception.service.ManagerServiceException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManagerService implements Runnable {

    private final ExecutorService executorManager;
    private final List<Runnable> services;
    private final ErrorHandler errorHandler;
    private final IslandApplication application;
    private final ShutdownListener shutdownListener;

    public ManagerService(IslandApplication application, ShutdownListener shutdownListener, int corePoolSize) {
        executorManager = Executors.newWorkStealingPool(corePoolSize);
        this.services = application.getServices();
        this.errorHandler = application.getErrorHandler();
        this.application = application;
        this.shutdownListener = shutdownListener;
    }

    @Override
    public void run() {
        try {
            if (application.isFinished()) {
                shutdownListener.onShutdown();
            } else {
                services.forEach(service ->
                        executorManager
                                .execute(wrapWithErrorHandling(service)));
            }
        } catch (RuntimeException e) {
            errorHandler.handle(new ManagerServiceException(e));
            shutdownListener.onShutdown();
        }
    }

    public Runnable wrapWithErrorHandling(Runnable task) {
        return () -> {
            try {
                task.run();
            } catch (RuntimeException e) {
                errorHandler.handle(e);
                shutdownListener.onShutdown();
            }
        };
    }

    public void shutdown() {
        executorManager.shutdown();
    }
}
