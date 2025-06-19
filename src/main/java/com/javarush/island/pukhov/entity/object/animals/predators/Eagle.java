package com.javarush.island.pukhov.entity.object.animals.predators;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

@Default(icon = "\uD83E\uDD85")
public class Eagle extends Predator{
    public Eagle(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
