package com.javarush.island.pukhov.exception;

public interface ErrorHandler {
    void handle(RuntimeException exception);
}
