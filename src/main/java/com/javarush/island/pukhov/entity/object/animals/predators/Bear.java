package com.javarush.island.pukhov.entity.object.animals.predators;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObjectIsland;

@Default(icon = "🐻")
public class Bear extends Predator{
    public Bear(String icon, ConfigurationObjectIsland config) {
        super(icon, config);
    }
}
