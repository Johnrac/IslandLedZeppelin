package com.javarush.island.pukhov.entity.object.animals.herbivores;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObject;

/*
TODO: Не только травоядное
 */
@Default(icon = "🐁")
public class Mouse extends Herbivore{
    public Mouse(String icon, ConfigurationObject config) {
        super(icon, config);
    }
}
