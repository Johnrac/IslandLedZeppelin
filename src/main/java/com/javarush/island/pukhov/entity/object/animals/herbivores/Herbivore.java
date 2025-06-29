package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.config.ConfigurationObject;
import com.javarush.island.pukhov.entity.object.animals.Animal;

public abstract class Herbivore extends Animal {
    protected Herbivore(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
