package com.javarush.island.pukhov.entity.factory;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.config.ConfigurationObjectIsland;
import com.javarush.island.pukhov.config.Settings;
import com.javarush.island.pukhov.constant.ConstantsDefault;
import com.javarush.island.pukhov.entity.map.Location;
import com.javarush.island.pukhov.entity.object.ClassProvider;
import com.javarush.island.pukhov.entity.object.ObjectIsland;
import com.javarush.island.pukhov.exception.ApplicationException;
import com.javarush.island.pukhov.exception.constants.Error;
import com.javarush.island.pukhov.util.AnnotationProcessor;
import com.javarush.island.pukhov.util.Rnd;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreatorObjectsIsland {
    private Set<ObjectIsland> prototypes = new HashSet<>();

    public CreatorObjectsIsland() {
        createPrototypes();
    }

    public void fill(Location location) {
        var objectsIsland = location.getObjectsLocation();
        for (ObjectIsland prototype : prototypes) {
            tryAddObject(prototype, objectsIsland, location.getLock());
        }
    }

    @SneakyThrows
    private void tryAddObject(ObjectIsland prototype, Map<String, Set<ObjectIsland>> objectsIsland,
                              ReentrantLock lock) {
        boolean fill = Rnd.getBoolean();
        if (fill) {
            String type = prototype.getType();
            try {
                if (lock.tryLock(ConstantsDefault.LOCK_WAIT_UNIT, ConstantsDefault.TYPE_TIME_WAIT_LOCK)) {
                    objectsIsland.putIfAbsent(type, new LinkedHashSet<>());
                    Set<ObjectIsland> objectsType = objectsIsland.get(type);
                    int currentCount = objectsType.size();
                    int max = prototype.getConfiguration().getMaxCount() - currentCount;
                    int count = Rnd.get(max);
                    for (int i = 0; i < count; i++) {
                        objectsType.add(prototype.clone());
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private void createPrototypes() {
        Settings settings = Settings.get();
        Path pathToPrototypes = settings.getPathToObjectsIsland();
        ClassProvider classProvider = new ClassProvider(pathToPrototypes);
        Set<Class<?>> classes = classProvider.getClasses();

        prototypes = classes.stream()
                .flatMap(clazz -> {
                    var configObject = settings.getConfigurationsObjects().get(clazz.getSimpleName());
                    if (Objects.nonNull(configObject)) {
                        final String icon = determineIcon(clazz);
                        try {
                            ObjectIsland instance = (ObjectIsland) clazz.getConstructor(String.class, ConfigurationObjectIsland.class)
                                    .newInstance(icon, configObject);
                            return Stream.of(instance);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                 NoSuchMethodException e) {
                            throw new ApplicationException(Error.NOT_FOUND_CONSTRUCTOR.formatted(clazz), e);
                        }
                    }
                    return Stream.empty();
                })
                .collect(Collectors.toSet());
    }

    private String determineIcon(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Default.class)) {
            return getIcon(clazz);
        } else if (ObjectIsland.class.isAnnotationPresent(Default.class)) {
            return getIcon(ObjectIsland.class);
        } else {
            return "O";
        }
    }

    private static String getIcon(Class<?> clazz) {
        return (String) AnnotationProcessor.
                getMapAttributesFor(Default.class, clazz)
                .get("icon");
    }
}
