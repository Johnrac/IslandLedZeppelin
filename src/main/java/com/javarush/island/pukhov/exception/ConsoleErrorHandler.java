package com.javarush.island.pukhov.exception;

import com.javarush.island.pukhov.output.Printer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleErrorHandler implements ErrorHandler{

    private final Printer printer;

    @Override
    public void handle(RuntimeException exception) {
        printer.printError(exception);
    }
}
