package com.javarush.island.pukhov.entity;

import com.javarush.island.pukhov.entity.factory.CreatorObjectsIsland;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.entity.map.Location;
import com.javarush.island.pukhov.entity.object.animals.Animal;
import com.javarush.island.pukhov.exception.ErrorHandler;
import com.javarush.island.pukhov.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class IslandApplication {
    private final IslandMap islandMap;
    private final CreatorObjectsIsland entityFactory;
    private final View view;
    private final List<Runnable> services;
    private final ErrorHandler errorHandler;

    public IslandApplication(IslandMap islandMap, CreatorObjectsIsland entityFactory, View view, List<Runnable> services, ErrorHandler errorHandler) {
        this.islandMap = islandMap;
        this.entityFactory = entityFactory;
        this.view = view;
        this.services = services;
        this.errorHandler = errorHandler;
    }

    public boolean isFinished() {
        return islandMap.getStreamLocations()
                .parallel()
                .map(Location::getSetUniquieObjects)
                .flatMap(Set::stream)
                .noneMatch(Animal.class::isInstance);
    }
}
