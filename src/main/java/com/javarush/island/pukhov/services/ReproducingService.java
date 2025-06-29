package com.javarush.island.pukhov.services;

import com.javarush.island.pukhov.api.entity.Reproducible;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.exception.ErrorHandler;

public class ReproducingService extends AbstractLocationService {
    public ReproducingService(ErrorHandler errorHandler, IslandMap map) {
        super(errorHandler, map);
    }

    @Override
    public void run() {
        try {
            processObjects(((obj, loc) -> {
                if (obj instanceof Reproducible reproducible) {
                    reproducible.reproduce(loc);
                }
            }));
        } catch (RuntimeException e) {
            errorHandler.handle(e);
        }
    }
}
