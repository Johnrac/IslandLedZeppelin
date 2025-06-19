package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

/*
TODO: Не только травоядный
 */
@Default(icon = "🐗")
public class Boar extends Herbivore {
    public Boar(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
