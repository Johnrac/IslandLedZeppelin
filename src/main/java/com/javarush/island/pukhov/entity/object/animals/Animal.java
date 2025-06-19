package com.javarush.island.pukhov.entity.object.animals;

import com.javarush.island.pukhov.api.entity.Eater;
import com.javarush.island.pukhov.api.entity.Moveable;
import com.javarush.island.pukhov.config.ConfigurationObject;
import com.javarush.island.pukhov.entity.map.Location;
import com.javarush.island.pukhov.entity.object.ObjectIsland;

public abstract class Animal extends ObjectIsland implements Eater, Moveable {


    protected Animal(String icon, ConfigurationObject config) {
        super(icon, config);
    }

    @Override
    public void move(Location location) {

    }

    @Override
    public void reproduce(Location location) {

    }
}
