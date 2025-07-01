package com.javarush.island.pukhov;


import com.javarush.island.pukhov.concurrent.Period;
import com.javarush.island.pukhov.config.Settings;
import com.javarush.island.pukhov.entity.IslandApplication;
import com.javarush.island.pukhov.entity.IslandProcessor;
import com.javarush.island.pukhov.entity.factory.CreatorObjectsIsland;
import com.javarush.island.pukhov.entity.factory.IslandMapCreator;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.exception.ConsoleErrorHandler;
import com.javarush.island.pukhov.exception.ErrorHandler;
import com.javarush.island.pukhov.output.ConsolePrinter;
import com.javarush.island.pukhov.output.Printer;
import com.javarush.island.pukhov.services.EatingService;
import com.javarush.island.pukhov.services.MovementService;
import com.javarush.island.pukhov.services.ReproducingService;
import com.javarush.island.pukhov.services.ViewService;
import com.javarush.island.pukhov.view.ConsoleView;
import com.javarush.island.pukhov.view.View;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleRunner {

    public static void main(String[] args) {

        Settings settings = Settings.get(args);

        Printer printer = new ConsolePrinter();
        ErrorHandler errorHandler = new ConsoleErrorHandler(printer);

        IslandApplication application = getIslandApplication(settings, printer, errorHandler);

        Period period = new Period(0, 1, TimeUnit.SECONDS);

        final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE);

        IslandProcessor processor = new IslandProcessor(application, period, executorService);
        processor.start();
    }

    private static IslandApplication getIslandApplication(Settings settings, Printer printer, ErrorHandler errorHandler) {
        CreatorObjectsIsland entityFactory = new CreatorObjectsIsland();
        IslandMapCreator mapCreator = new IslandMapCreator(entityFactory);
        IslandMap islandMap = mapCreator.createRandomFilledMap(settings.getRowCount(), settings.getColumnCount());
        View view = new ConsoleView(islandMap, printer);

        List<Runnable> services = List.of(
                new ViewService(view),
                new EatingService(islandMap),
                new MovementService(islandMap),
                new ReproducingService(islandMap)
        );

        return new IslandApplication(islandMap, entityFactory, view, services, errorHandler);
    }
}
