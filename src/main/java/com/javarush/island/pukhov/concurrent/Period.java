package com.javarush.island.pukhov.concurrent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Getter
public class Period {
    private final long delay;
    private final int periodTime;
    private final TimeUnit timeUnit;
}
