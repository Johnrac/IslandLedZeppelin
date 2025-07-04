package com.javarush.island.pukhov.entity.map;

import com.javarush.island.pukhov.api.concurrent.Lockable;
import com.javarush.island.pukhov.entity.object.ObjectIsland;
import com.javarush.island.pukhov.util.Rnd;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Getter
public class Location implements Lockable {

    private final ReentrantLock lock = new ReentrantLock();
    private final Map<String, Set<ObjectIsland>> objectsLocation = new ConcurrentHashMap<>();

    @Getter(AccessLevel.NONE)
    private static final int MAX_COUNT_DIRECTIONS = 8;
    @Getter(AccessLevel.NONE)
    private final List<Location> possibleLocations = new ArrayList<>(MAX_COUNT_DIRECTIONS);


    public void updateNextLocation(IslandMap map, int row, int col) {
        int maxRow = map.getRows() - 1;
        int maxCol = map.getColumns() - 1;

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow != 0 || dCol != 0) {
                    int newRow = row + dRow;
                    int newCol = col + dCol;
                    if (newRow >= 0 && newCol >= 0 && newCol <= maxCol && newRow <= maxRow) {
                        possibleLocations.add(map.getLocation(newRow, newCol));
                    }
                }
            }
        }
    }

    public Location getNextLocation(int countStep) {
        Set<Location> visitedLocations = new HashSet<>();
        Location currentLocation = this;
        visitedLocations.add(currentLocation);

        while (visitedLocations.size()  < countStep) {
            List<Location> nextLocations = currentLocation.possibleLocations.stream()
                    .filter(location -> !visitedLocations.contains(location))
                    .toList();
            if (nextLocations.isEmpty()) {
                break;
            }
                int index = Rnd.get(nextLocations.size());
                currentLocation = nextLocations.get(index);
                visitedLocations.add(currentLocation);
        }
        return currentLocation;
    }

    public Map<String, Integer> getStatistic() {
        try {
            lock.lock();
            return objectsLocation.values().stream()
                    .filter(object -> !object.isEmpty())
                    .collect(Collectors.toMap(set -> set.iterator().next().getIcon(), Set::size));
        } finally {
            lock.unlock();
        }
    }

    public Set<ObjectIsland> getSetUniquieObjects() {
        try {
            lock.lock();
            return objectsLocation.values().stream()
                    .filter(set -> !set.isEmpty())
                    .map(set -> set.iterator().next())
                    .collect(Collectors.toSet());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return objectsLocation.entrySet().stream()
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
