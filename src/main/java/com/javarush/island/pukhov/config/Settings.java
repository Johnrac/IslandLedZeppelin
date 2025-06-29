package com.javarush.island.pukhov.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.island.pukhov.api.annotation.Path;
import com.javarush.island.pukhov.exception.ConfigurationException;
import com.javarush.island.pukhov.exception.constants.Error;
import com.javarush.island.pukhov.util.AnnotationProcessor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@ToString
@Path("/pukhov/settings.yaml")
public class Settings {

    private static volatile Settings settings; //NOSONAR

    private static volatile String pathToSettings;

    public static Settings get(String... args) {
        Settings localSettings = settings;
        if (Objects.isNull(localSettings)) {
            synchronized (Settings.class) {
                localSettings = settings;
                if (Objects.isNull(localSettings)) {
                    pathToSettings =new ParserSettings(args).parsePathToSettings();
                    if (pathToSettings.isEmpty()) {
                        Map<String, Object> path = AnnotationProcessor.getMapAttributesFor(Path.class, Settings.class);
                        pathToSettings = (String) Optional.ofNullable(path.get("value")).orElse("");
                    }
                    localSettings = new Settings();
                    settings = localSettings;
                }
            }
        }
        return localSettings;
    }

    public static Settings get() {
        return get(new String[0]);
    }

    @Getter
    private int columnCount;
    @Getter
    private int rowCount;
    @Getter
    private final ConsoleSettings consoleSettings = new ConsoleSettings();

    @JsonDeserialize(using = PathDeserializer.class)
    @Getter
    private java.nio.file.Path pathToObjectsIsland;
    @Getter
    private Map<String,ConfigurationObject> configurationsObjects;

    private Settings() {
        if (pathToSettings.isEmpty()) {
            throw new ConfigurationException(Error.NO_SETTINGS_FILE.toString(),new NullPointerException());
        }
        loadFromYaml();
    }

    @SneakyThrows
    private void loadFromYaml() {
        URL resource = this.getClass().getResource(pathToSettings);
        if (Objects.nonNull(resource)) {
            ObjectMapper mapper = new YAMLMapper();
            ObjectReader objectReader = mapper.readerForUpdating(this);
            objectReader.readValue(resource);
        } else {
            throw new ConfigurationException(Error.PATH_IS_NOT_CORRECT.formatted(pathToSettings),new IllegalArgumentException());
        }
    }


}
