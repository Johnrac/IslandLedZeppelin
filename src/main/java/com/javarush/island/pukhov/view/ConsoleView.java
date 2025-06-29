package com.javarush.island.pukhov.view;

import com.javarush.island.pukhov.config.ConsoleSettings;
import com.javarush.island.pukhov.config.Settings;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.entity.map.Location;
import com.javarush.island.pukhov.entity.object.ObjectIsland;
import com.javarush.island.pukhov.output.Printer;

import java.util.*;
import java.util.stream.Collectors;


public class ConsoleView implements View {

    private final IslandMap map;
    private final Printer printer;
    private final ConsoleSettings settings = Settings.get().getConsoleSettings();
    private final boolean isContinueRow = Settings.get().getRowCount() > settings.getShowRow();
    private final boolean isContinueColumn = Settings.get().getColumnCount() > settings.getShowColumn();

    private static final String BORDER = "|";
    private static final int COUNT_REPEAT = 130;
    private static final String SEPARATOR_LINE = "-".repeat(COUNT_REPEAT);
    private static final String CONTINUE_SYMBOL = "#";
    private static final String CONTINUE_LINE_ROW = CONTINUE_SYMBOL.repeat(COUNT_REPEAT);

    public ConsoleView(IslandMap map, Printer printer) {
        this.map = map;
        this.printer = printer;
    }


    @Override
    public void showIsland() {

        int size = settings.getShowColumn() * settings.getShowRow();
        Queue<String> viewsObjects = getViewsObjects(size);

        int columnWidth = viewsObjects.stream()
                .parallel()
                .mapToInt(this::getVisualWidth)
                .max()
                .orElse(1);

        String viewMap = getViewMap(viewsObjects, columnWidth);

        printer.println(SEPARATOR_LINE);
        printer.print(viewMap);
        if (isContinueRow) {
            printer.println(CONTINUE_LINE_ROW);
        }
        printer.println(SEPARATOR_LINE);
    }

    private Queue<String> getViewsObjects(int sizeList) {
        Queue<String> viewsObjects = new ArrayDeque<>(sizeList);
        for (int i = 0; i < settings.getShowRow(); i++) {
            for (int j = 0; j < settings.getShowColumn(); j++) {
                Location location = map.getLocation(i, j);
                viewsObjects.add(getViewObjects(location));
            }
        }
        return viewsObjects;
    }

    private int getVisualWidth(String str) {
        return str.codePoints()
                .mapToObj(cp -> new String(Character.toChars(cp)))
                .mapToInt(this::getVisualWidthSymbol)
                .sum();
    }

    private int getVisualWidthSymbol(String str) {
        return switch (str) {
            case "\uD83D\uDC3A", "🦊", "\uD83E\uDD85", "🐑", "🐴",
                 "🐇", "🐁", "🐐", "🦌", "🐛", "🐃", "🐗", "🐻", "\uD83D\uDC0D" -> 3;
            default -> 2;
        };
    }

    private String getViewMap(Queue<String> viewsObjects, int columnWidth) {
        StringBuilder viewMap = new StringBuilder();
        for (int i = 0; i < settings.getShowRow(); i++) {
            for (int j = 0; j < settings.getShowColumn(); j++) {
                viewMap.append(BORDER).append(" ").append(getStringColumn(viewsObjects.poll(), columnWidth)).append(" ");
            }
            viewMap.append(BORDER);
            if (isContinueColumn) {
                viewMap.append(CONTINUE_SYMBOL);
            }
            viewMap.append(System.lineSeparator());
        }
        return viewMap.toString();
    }


    private String getStringColumn(String viewObjects, int columnWidth) {
        int visualWidth = getVisualWidth(viewObjects);
        int countSpace = columnWidth - visualWidth;
        return viewObjects + " ".repeat(countSpace);
    }

    private String getViewObjects(Location location) {
        return location.getSetUniquieObjects().stream()
                .map(ObjectIsland::getIcon)
                .collect(Collectors.joining());
    }

    @Override
    public void showStatistic() {
        printer.println("Statistic:" + map.getStatistic());
    }

    @Override
    public void showFinish() {
        printer.println("SIMULATION FINISHED");
    }
}
