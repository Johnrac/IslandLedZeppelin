package com.javarush.island.pukhov.entity.object;

import com.javarush.island.pukhov.config.UnitCount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectIslandTest {
    @Test
    void getCountObjects() {
        int size = 10, countReproduce = 10;
        UnitCount unitCount = UnitCount.PERCENT;
        int countObjects = switch(unitCount) {
            case UNIT -> countReproduce;
            case PERCENT -> Math.ceilDiv(countReproduce * size, 100);
        };
        Assertions.assertEquals(1, countObjects);
    }
}