package com.javarush.island.pukhov.services;

import com.javarush.island.pukhov.api.entity.Reproducible;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.exception.service.ReproducingServiceException;

public class ReproducingService extends AbstractLocationService {

    public ReproducingService(IslandMap map) {
        super(map);
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
            throw new ReproducingServiceException(e);
        }
    }
}
