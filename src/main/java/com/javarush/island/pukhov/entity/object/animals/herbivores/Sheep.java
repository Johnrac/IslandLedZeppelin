package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObjectIsland;

@Default(icon = "🐑")
public class Sheep extends Herbivore {
    public Sheep(String icon, ConfigurationObjectIsland config) {
        super(icon, config);
    }
}
