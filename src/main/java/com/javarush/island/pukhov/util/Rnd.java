package com.javarush.island.pukhov.util;

import java.util.concurrent.ThreadLocalRandom;

public final class Rnd {
    private Rnd(){}

    public static int get(int max) {
       return ThreadLocalRandom.current().nextInt(max);
    }

    public static boolean getBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static double get(double min, int max) {
        return ThreadLocalRandom.current().nextDouble();
    }
}
