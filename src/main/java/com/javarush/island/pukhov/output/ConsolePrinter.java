package com.javarush.island.pukhov.output;

public class ConsolePrinter implements Printer{
    @SuppressWarnings("java:S106")
    @Override
    public void print(Object information) {
        System.out.print(information);
    }
    @SuppressWarnings("java:S106")
    @Override
    public void println(Object information) {
        System.out.println(information);
    }

    @Override
    public void printError(RuntimeException exception) {
        exception.printStackTrace();
    }
}
