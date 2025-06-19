package com.javarush.island.pukhov.entity.object.animals.predators;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

@Default(icon = "\uD83D\uDC0D")
public class Boa extends Predator{
    public Boa(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
