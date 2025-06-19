package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

/*
TODO: Не только травоядная
 */
@Default(icon = "🦆")
public class Duck extends Herbivore{
    public Duck(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
