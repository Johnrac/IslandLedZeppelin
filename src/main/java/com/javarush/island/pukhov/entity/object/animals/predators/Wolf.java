package com.javarush.island.pukhov.entity.object.animals.predators;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

@Default(icon = "\uD83D\uDC3A")
public class Wolf extends Predator {

    public Wolf(String icon, ConfigurationObject config) {
        super(icon, config);
    }

}
