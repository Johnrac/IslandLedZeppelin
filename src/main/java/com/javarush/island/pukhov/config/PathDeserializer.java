package com.javarush.island.pukhov.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.javarush.island.pukhov.exception.ApplicationException;
import com.javarush.island.pukhov.exception.constants.Error;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.Optional;

public class PathDeserializer extends JsonDeserializer<Path> {

    @SneakyThrows
    @Override
    public Path deserialize(JsonParser p, DeserializationContext ctxt) {
        return Path.of(Optional
                .ofNullable(this.getClass().getResource(p.getText()))
                .orElseThrow(() -> new ApplicationException(new IllegalArgumentException(Error.NOT_A_PATH.toString())))
                .toURI());
    }
}
