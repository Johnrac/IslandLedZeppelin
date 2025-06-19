package com.javarush.island.pukhov.entity.map;

import com.javarush.island.pukhov.entity.object.ObjectIsland;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Getter
public class Location {

    private final ReentrantLock lock = new ReentrantLock();
    private final Map<String, Set<ObjectIsland>> objectsIsland = new ConcurrentHashMap<>();

    @Getter(AccessLevel.NONE)
    private static final int MAX_COUNT_DIRECTIONS = 8;

    private final List<Location> nextLocation = new ArrayList<>(MAX_COUNT_DIRECTIONS);


    public void updateNextLocation(IslandMap map, int row, int col) {
        int maxRow = map.getRows() - 1;
        int maxCol = map.getColumns() - 1;

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow != 0 || dCol != 0) {
                    int newRow = row + dRow;
                    int newCol = col + dCol;
                    if (newRow >= 0 && newCol >= 0 && newCol <= maxCol && newRow <= maxRow) {
                        nextLocation.add(map.getLocation(newRow, newCol));
                    }
                }
            }
        }
    }

    public Map<String, Integer> getStatistic() {
        try {
            lock.lock();
            return objectsIsland.values().stream()
                    .parallel()
                    .filter(object -> !object.isEmpty())
                    .collect(Collectors.toMap(set -> set.iterator().next().getIcon(), Set::size));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return objectsIsland.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> {
                    int countObjects = entry.getValue().size();
                    String name = entry.getKey();
                    String icon = entry.getValue().stream().map(ObjectIsland::getIcon).findFirst().orElse("");
                    return name + ":" + countObjects + icon;
                })
                .collect(Collectors.joining(","));
    }
}
