package com.javarush.island.pukhov.entity.object.animals.predators;

import com.javarush.island.pukhov.config.ConfigurationObject;
import com.javarush.island.pukhov.entity.object.animals.Animal;

public abstract class Predator extends Animal {

    protected Predator(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
