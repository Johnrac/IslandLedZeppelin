package com.javarush.island.pukhov.exception.constants;


public enum Error {
    NO_SETTINGS_FILE ("settings.yaml is not found"),
    PATH_IS_NOT_CORRECT ("Path \"%s\" is not exists"),
    NOT_A_PATH ("Argument is not a path"),
    NOT_FOUND_CONSTRUCTOR ("not found constructor for entity %s");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public String formatted(Object message) {
        return this.message.formatted(message);
    }
}
