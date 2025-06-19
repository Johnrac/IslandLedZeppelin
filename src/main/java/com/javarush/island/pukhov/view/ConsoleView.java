package com.javarush.island.pukhov.view;

import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.output.Printer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleView implements View {
    private final IslandMap map;
    private final Printer printer;


    @Override
    public void showIsland() {
        /* Для каждой локации вывести количество животных */

    }

    @Override
    public void showStatistic() {
        printer.print(map.getStatistic());
    }
}
