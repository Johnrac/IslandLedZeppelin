package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.config.ConfigurationObjectIsland;
import com.javarush.island.pukhov.entity.object.animals.Animal;

public abstract class Herbivore extends Animal {
    protected Herbivore(String icon, ConfigurationObjectIsland config) {
        super(icon, config);
    }
}
