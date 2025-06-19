package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

@Default(icon = "🐛")
public class Caterpillar extends Herbivore{
    public Caterpillar(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
