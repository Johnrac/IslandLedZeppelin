package com.javarush.island.pukhov.output;

public interface Printer {
    void print(Object information);
    void printError(RuntimeException exception);
}
