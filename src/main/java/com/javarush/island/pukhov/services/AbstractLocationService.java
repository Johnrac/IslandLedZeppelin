package com.javarush.island.pukhov.services;

import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.entity.map.Location;
import com.javarush.island.pukhov.entity.object.ObjectIsland;
import com.javarush.island.pukhov.exception.ErrorHandler;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractLocationService implements Runnable {
    protected final ErrorHandler errorHandler;
    private final IslandMap map;

    public void processObjects(BiConsumer<ObjectIsland, Location> action) {
        map.getStreamLocations()
                .forEach(location ->
                        safeReadObjects(location)
                                .forEach(obj -> action.accept(obj, location))
                );
    }

    private Stream<ObjectIsland> safeReadObjects(Location location) {
        try {
            location.getLock().lock();
            Set<ObjectIsland> clone = location.getObjectsLocation()
                    .values()
                    .stream()
                    .parallel()
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            return clone.stream();

        } finally {
            location.getLock().unlock();
        }
    }
}

