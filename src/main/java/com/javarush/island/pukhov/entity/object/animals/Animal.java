package com.javarush.island.pukhov.entity.object.animals;

import com.javarush.island.pukhov.api.entity.Eater;
import com.javarush.island.pukhov.api.entity.Moveable;
import com.javarush.island.pukhov.concurrent.DoubleLock;
import com.javarush.island.pukhov.config.ConfigurationObjectIsland;
import com.javarush.island.pukhov.constant.ConstantsDefault;
import com.javarush.island.pukhov.entity.map.Location;
import com.javarush.island.pukhov.entity.object.ObjectIsland;
import com.javarush.island.pukhov.util.Rnd;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public abstract class Animal extends ObjectIsland implements Eater, Moveable {


    private final Map<String, Integer> mapFood;

    protected Animal(String icon, ConfigurationObjectIsland config) {
        super(icon, config);
        this.mapFood = config.getMapFood();
    }

    @SneakyThrows
    @Override
    public void eat(Location location) {

        boolean locked = location.getLock().tryLock(ConstantsDefault.LOCK_WAIT_UNIT, ConstantsDefault.TYPE_TIME_WAIT_LOCK);
        try {
            if (locked) {
                processEating(location.getObjectsLocation());
            }
        } finally {
            if (locked) {
                location.getLock().unlock();
            }
        }
    }

    private void processEating(Map<String, Set<ObjectIsland>> objectsIsland) {
        if (!tryEatFood(objectsIsland)) {
            loseWeight();
        }
        if (getWeight() <= 0) {
            die(objectsIsland);
        }
    }

    private void die(Map<String, Set<ObjectIsland>> objectsIsland) {
        Set<ObjectIsland> objectsType = objectsIsland.get(getType());
        if (Objects.nonNull(objectsType)) {
            objectsType.remove(this);
        }
    }

    private void loseWeight() {
        setWeight(getWeight() - 1);
    }

    private boolean tryEatFood(Map<String, Set<ObjectIsland>> objectsLocation) {
        boolean hasEaten = false;
        double totalFoodConsumed = 0d;
        Map<String, Integer> availableFood = getAvailableFood(objectsLocation);
        RandomTypeFoodLocator foodSelector = new RandomTypeFoodLocator(availableFood);
        boolean noMoreFood = false;

        while (isHungry(totalFoodConsumed) && !noMoreFood) {
            String selectedFoodType = foodSelector.getFood();
            noMoreFood = selectedFoodType.isEmpty();
            if (!noMoreFood) {
                totalFoodConsumed = eat(objectsLocation, selectedFoodType, totalFoodConsumed, availableFood);
                hasEaten = true;
            }
        }
        return hasEaten;
    }

    private Map<String, Integer> getAvailableFood(Map<String, Set<ObjectIsland>> objectsLocation) {
        return getMapFood().entrySet().stream()
                .filter(entry -> objectsLocation.containsKey(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isHungry(Double amountFoodEaten) {
        ConfigurationObjectIsland configuration = getConfiguration();
        double needCountFood = configuration.getNeedCountFood();
        int maxWeight = configuration.getMaxWeight();
        return amountFoodEaten < needCountFood && getWeight() < maxWeight;
    }

    private double eat(Map<String, Set<ObjectIsland>> objectsLocation,
                       String typeFood,
                       double amountFoodEaten,
                       Map<String, Integer> foodInLocation) {

        Set<ObjectIsland> objectsType = objectsLocation.get(typeFood);
        if (!objectsType.isEmpty()) {
            Iterator<ObjectIsland> iteratorVictim = objectsType.iterator();
            ObjectIsland victim = iteratorVictim.next();
            double victimWeight = victim.getWeight();

            iteratorVictim.remove();

            setWeight(getWeight() + victimWeight);
            amountFoodEaten += victimWeight;
        } else {
            objectsLocation.remove(typeFood);
            foodInLocation.remove(typeFood);
        }
        return amountFoodEaten;
    }

    @Override
    public void move(Location startLocation) {
        int maxSpeed = getConfiguration().getMaxSpeed();
        if (maxSpeed > 0) {
            int countStep = Rnd.get(maxSpeed);
            Location endLocation = startLocation.getNextLocation(countStep);
            safeMove(startLocation, endLocation);
        }
    }

    @SneakyThrows
    private void safeMove(Location sourceLocation, Location destinationLocation) {
        DoubleLock doubleLock = new DoubleLock(sourceLocation, destinationLocation);
        boolean locked = false;
        try {
            locked = doubleLock.tryLock(ConstantsDefault.LOCK_WAIT_UNIT, ConstantsDefault.TYPE_TIME_WAIT_LOCK);
            if (locked) {
                processMove(sourceLocation, destinationLocation);
            }
        } catch (Exception e) {
            if (locked) {
                rollbackMove(sourceLocation, destinationLocation);
            }
            throw e;
        } finally {
            if (locked) {
                doubleLock.unlock();
            }
        }
    }


    private boolean processMove(Location sourceLocation, Location destinationLocation) {
        pollFrom(sourceLocation);
        addTo(destinationLocation);
        return true;
    }

    private void rollbackMove(Location sourceLocation, Location destinationLocation) {
        addTo(sourceLocation);
        pollFrom(destinationLocation);
    }

    private void addTo(Location location) {
        Map<String, Set<ObjectIsland>> objectsMap = location.getObjectsLocation();
        Set<ObjectIsland> set = objectsMap.computeIfAbsent(getType(), v -> new HashSet<>());
        set.add(this);
    }

    private void pollFrom(Location location) {
        Map<String, Set<ObjectIsland>> objectsMap = location
                .getObjectsLocation();
        Set<ObjectIsland> set = objectsMap.get(getType());

        if (Objects.nonNull(set)) {
            set.remove(this);
            if (set.isEmpty()) {
                objectsMap.remove(getType());
            }
        }
    }
}
