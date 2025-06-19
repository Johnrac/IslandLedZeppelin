package com.javarush.island.pukhov.entity.object.animals.predators;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

@Default(icon = "🦊")
public class Fox extends Predator {
    public Fox(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
