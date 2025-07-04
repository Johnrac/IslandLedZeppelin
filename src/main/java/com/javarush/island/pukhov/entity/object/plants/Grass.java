package com.javarush.island.pukhov.entity.object.plants;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObjectIsland;
import com.javarush.island.pukhov.entity.object.ObjectIsland;

@Default(icon = "\uD83C\uDF31")
public class Grass extends ObjectIsland {

    public Grass(String icon, ConfigurationObjectIsland config) {
        super(icon, config);
    }
    
}
