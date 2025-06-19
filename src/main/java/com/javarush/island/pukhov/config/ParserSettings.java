package com.javarush.island.pukhov.config;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class ParserSettings {
    private final String[] args;

    public String parsePathToSettings() {
        return Arrays
                .stream(args)
                .filter(s -> s.toUpperCase().contains("CONFIG"))
                .map(s -> s.split("="))
                .filter(array -> array.length > 1)
                .map(array -> array[1].trim())
                .findFirst()
                .orElse("");
    }
}
