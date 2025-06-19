package com.javarush.island.pukhov.entity.map;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IslandMap {

    private final Location[][] locations;

    public IslandMap(int rows, int columns) {
        this.locations = new Location[rows][columns];
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                locations[i][j] = new Location();
            }
        }
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[i].length; j++) {
                locations[i][j].updateNextLocation(this, i, j);
            }
        }
    }

    public Map<String, Long> getStatistic() {
        return getStreamLocations()
                .parallel()
                .map(Location::getStatistic)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Long.valueOf(entry.getValue()),
                        Long::sum
                ));
    }

    public Stream<Location> getStreamLocations() {
        return Arrays.stream(locations)
                .flatMap(Arrays::stream);
    }

    public Location getLocation(int row, int column) {
        return locations[row][column];
    }

    public int getColumns() {
        return locations[0].length;
    }

    public int getRows() {
        return locations.length;
    }

}
