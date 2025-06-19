package com.javarush.island.pukhov.entity.factory;

import com.javarush.island.pukhov.entity.map.IslandMap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IslandMapCreator {

    private final CreatorObjectsIsland creator;

    public IslandMap createRandomFilledMap(int countRows, int countColumns) {
        IslandMap islandMap = new IslandMap(countRows, countColumns);
        islandMap.getStreamLocations()
                .forEach(creator::fill);
        return islandMap;
    }
}
