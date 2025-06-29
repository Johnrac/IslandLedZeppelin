package com.javarush.island.pukhov.entity.object.animals;

import com.javarush.island.pukhov.util.Rnd;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class RandomTypeFoodLocator {
    private final Map<String,Integer> mapFood;

    public String getFood() {
        final int totalSum = getTotalSum() + 1;
        final int random = Rnd.get(totalSum);

        return selectFoodByWeight(random);
    }

    private String selectFoodByWeight(int random) {
        int accumulativeSum = 0;
        for (Map.Entry<String, Integer> entry : mapFood.entrySet()) {
            accumulativeSum += entry.getValue();
            if (random <= accumulativeSum) {
                return entry.getKey();
            }
        }
        return "";
    }

    private int getTotalSum() {
        return mapFood.values().stream().mapToInt(Integer::intValue).sum();
    }
}
