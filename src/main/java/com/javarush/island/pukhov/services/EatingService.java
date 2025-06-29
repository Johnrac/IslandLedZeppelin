package com.javarush.island.pukhov.services;

import com.javarush.island.pukhov.api.entity.Eater;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.exception.ErrorHandler;

public class EatingService extends AbstractLocationService {
    public EatingService(ErrorHandler errorHandler, IslandMap map) {
        super(errorHandler, map);
    }

    @Override
    public void run() {
        try {
            processObjects(((obj, loc) -> {
                if (obj instanceof Eater eater) {
                    eater.eat(loc);
                }
            }));
        } catch (RuntimeException e) {
            errorHandler.handle(e);
        }
    }
}
