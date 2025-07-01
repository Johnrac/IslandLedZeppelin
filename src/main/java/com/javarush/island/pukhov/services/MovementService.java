package com.javarush.island.pukhov.services;

import com.javarush.island.pukhov.api.entity.Moveable;
import com.javarush.island.pukhov.entity.map.IslandMap;
import com.javarush.island.pukhov.exception.service.MovementServiceException;

public class MovementService extends AbstractLocationService {

    public MovementService(IslandMap map) {
        super(map);
    }

    @Override
    public void run() {
        try {
            processObjects(((obj, loc) -> {
                if (obj instanceof Moveable moveable) {
                    moveable.move(loc);
                }
            }));
        } catch (RuntimeException e) {
            throw new MovementServiceException(e);
        }
    }
}
