package com.javarush.island.pukhov.entity.object;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.api.entity.Reproducible;
import com.javarush.island.pukhov.config.ConfigurationObject;
import com.javarush.island.pukhov.config.UnitCount;
import com.javarush.island.pukhov.constant.ConstantsDefault;
import com.javarush.island.pukhov.entity.map.Location;
import com.javarush.island.pukhov.util.Rnd;
import lombok.*;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;


@Getter
@Default
@ToString(exclude = {"configuration"})
public abstract class ObjectIsland implements Reproducible, Cloneable {

    private static final AtomicLong counter = new AtomicLong();
    private static final int COUNT_REPRODUCE = 1;

    private final String icon;
    private final String type = getClass().getSimpleName();
    private final ConfigurationObject configuration;

    private long id = counter.incrementAndGet();
    @Setter(AccessLevel.PROTECTED)
    private double weight;

    protected ObjectIsland(String icon, ConfigurationObject config) {
        this.icon = icon;
        configuration = config;
        this.weight = config.getMaxWeight();

    }

    @SuppressWarnings("java:S2975")
    @SneakyThrows
    public ObjectIsland clone() {
        ObjectIsland clone = (ObjectIsland) super.clone();
        clone.id = counter.incrementAndGet();
        clone.weight = Rnd.get(1, configuration.getMaxWeight());
        return clone;
    }

    @SneakyThrows
    @Override
    public void reproduce(Location location) {
        boolean isReproduce = Rnd.getBoolean();
        boolean locked = location.getLock()
                .tryLock(ConstantsDefault.LOCK_WAIT_UNIT, ConstantsDefault.TYPE_TIME_WAIT_LOCK);
        try {
            if (isReproduce && locked) {
                processReproduction(location);
            }
        } finally {
            if (locked) {
                location.getLock().unlock();
            }
        }


    }

    private void processReproduction(Location location) {
        Set<ObjectIsland> objectsLocation = location.getObjectsLocation().get(getType());
        if (isPreparedReproduce(objectsLocation)) {
            reproduce(objectsLocation, COUNT_REPRODUCE, UnitCount.UNIT);
        }
    }

    private void reproduce(Set<ObjectIsland> objectsLocation, int countReproduce, UnitCount unitCount) {
        int countObjects = getCountObjects(objectsLocation.size(), countReproduce, unitCount);
        for (int i = 0; i < countObjects; i++) {
            objectsLocation.add(clone());
        }
    }

    private int getCountObjects(int size, int count, UnitCount unit) {
        return switch (unit) {
            case UNIT -> count;
            case PERCENT -> Math.ceilDiv(count * size, 100);
        };

    }

    private boolean isPreparedReproduce(Set<ObjectIsland> objectsLocation) {

        return Objects.nonNull(objectsLocation) && objectsLocation.size() > 1;
    }
}
