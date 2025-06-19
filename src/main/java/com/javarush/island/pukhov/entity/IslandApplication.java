package com.javarush.island.pukhov.entity;

import com.javarush.island.pukhov.entity.factory.CreatorObjectsIsland;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.view.View;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IslandApplication {

    private final IslandMap islandMap;
    private final CreatorObjectsIsland creatorObjectsIsland;
    private final View view;

    public void start() {
    }
}
