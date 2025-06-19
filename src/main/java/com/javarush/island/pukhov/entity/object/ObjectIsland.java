package com.javarush.island.pukhov.entity.object;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.api.entity.Reproducible;
import com.javarush.island.pukhov.config.ConfigurationObject;
import com.javarush.island.pukhov.util.Rnd;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;


@Getter
@Default
@ToString
public abstract class ObjectIsland implements Reproducible, Cloneable {

    private static final AtomicLong counter = new AtomicLong();

    private final String icon;
    private final Set<Map.Entry<String, Integer>> foodMap;
    private final String type = getClass().getSimpleName();
    private final ConfigurationObject configuration;

    private long id = counter.incrementAndGet();
    private double weight;

    protected ObjectIsland(String icon, ConfigurationObject config) {
        this.icon = icon;
        configuration = config;
        this.weight = config.getMaxWeight();
        this.foodMap = new TreeSet<>((entry1, entry2) -> entry2.getValue() - entry1.getValue());
        this.foodMap.addAll(config.getMapFood().entrySet());
    }

    @SuppressWarnings("java:S2975")
    @SneakyThrows
    public ObjectIsland clone() {
        ObjectIsland clone = (ObjectIsland) super.clone();
        clone.id = counter.incrementAndGet();
        clone.weight = Rnd.get(1,configuration.getMaxWeight());
        return clone;
    }
}
