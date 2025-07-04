package com.javarush.island.pukhov.entity.object.animals.predators;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObjectIsland;

@Default(icon = "\uD83D\uDC0D")
public class Boa extends Predator{
    public Boa(String icon, ConfigurationObjectIsland config) {
        super(icon, config);
    }
}
