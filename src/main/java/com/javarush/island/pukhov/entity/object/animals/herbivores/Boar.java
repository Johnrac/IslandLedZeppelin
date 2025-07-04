package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObjectIsland;

@Default(icon = "🐗")
public class Boar extends Herbivore {
    public Boar(String icon, ConfigurationObjectIsland config) {
        super(icon, config);
    }
}
