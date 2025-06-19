package com.javarush.island.pukhov;


import com.javarush.island.pukhov.config.Settings;
import com.javarush.island.pukhov.entity.factory.CreatorObjectsIsland;
import com.javarush.island.pukhov.entity.factory.IslandMapCreator;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.exception.ConsoleErrorHandler;
import com.javarush.island.pukhov.exception.ErrorHandler;
import com.javarush.island.pukhov.output.ConsolePrinter;
import com.javarush.island.pukhov.output.Printer;
import com.javarush.island.pukhov.view.ConsoleView;
import com.javarush.island.pukhov.view.View;

public class ConsoleRunner {

    public static void main(String[] args) {

        Settings settings = Settings.get(args);

        Printer printer = new ConsolePrinter();
        ErrorHandler consoleHandler = new ConsoleErrorHandler(printer);

        CreatorObjectsIsland entityFactory = new CreatorObjectsIsland();
        IslandMapCreator mapCreator = new IslandMapCreator(entityFactory);
        IslandMap islandMap = mapCreator.createRandomFilledMap(settings.getRowCount(), settings.getColumnCount());
        View view = new ConsoleView(islandMap, printer);
//
//        List<Runnable> services = List.of(
//                new EatingService(consoleHandler),
//                new MovementService(consoleHandler),
//                new ReproducingService(consoleHandler)
//        );

//        IslandApplication islandApplication = new IslandApplication(islandMap, entityFactory, view, services);
//        islandApplication.start();
    }
}
