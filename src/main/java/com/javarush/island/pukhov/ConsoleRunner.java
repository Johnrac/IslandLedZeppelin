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
import java.util.concurrent.TimeUnit;

public class ConsoleRunner {

    public static void main(String[] args) {

        Settings settings = Settings.get(args);

        Printer printer = new ConsolePrinter();
        ErrorHandler consoleHandler = new ConsoleErrorHandler(printer);

        CreatorObjectsIsland entityFactory = new CreatorObjectsIsland();
        IslandMapCreator mapCreator = new IslandMapCreator(entityFactory);
        IslandMap islandMap = mapCreator.createRandomFilledMap(settings.getRowCount(), settings.getColumnCount());
        View view = new ConsoleView(islandMap, printer);

        List<Runnable> services = List.of(
                new ViewService(consoleHandler,view),
                new EatingService(consoleHandler,islandMap),
                new MovementService(consoleHandler,islandMap),
                new ReproducingService(consoleHandler,islandMap)
        );

        IslandApplication application = new IslandApplication(islandMap,entityFactory,view,services,consoleHandler);

        Period period = new Period(0,1, TimeUnit.SECONDS);
        IslandProcessor processor = new IslandProcessor(application, period);
        processor.start();
    }
}
