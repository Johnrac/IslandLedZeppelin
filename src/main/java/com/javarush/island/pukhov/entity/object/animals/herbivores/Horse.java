package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

@Default(icon = "🐴")
public class Horse extends Herbivore {
    public Horse(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
