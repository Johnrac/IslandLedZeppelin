package com.javarush.island.pukhov.entity.object;

import com.javarush.island.pukhov.api.annotation.Default;
import com.javarush.island.pukhov.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Default(icon = "O")
public class ClassProvider {
    private final Path pathToObjectsIsland;
    private static final String SUFFIX_CLASS = ".class";

    @SneakyThrows
    public Set<Class<?>> getClasses() {
        final Path rootPath = Path.of(this.getClass().getResource("/").toURI());
        try (Stream<Path> streamFiles = Files.walk(pathToObjectsIsland)) {
            return streamFiles
                    .parallel()
                    .filter(p -> p.toString().endsWith(SUFFIX_CLASS))
                    .map(p -> loadClass(p, rootPath))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
    }

    private Class<?> loadClass(Path p, Path rootPath) {
        String className = getClassName(p, rootPath);
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ApplicationException(e);
        }
        return clazz;
    }

    private String getClassName(Path pathToClass, Path rootClassPath) {
        Path relativizePath = rootClassPath.relativize(pathToClass);
        return convertToClassName(relativizePath);
    }

    private String convertToClassName(Path relativizePath) {
        String pathStr = relativizePath.toString().replace(File.separatorChar, '.');
        if (pathStr.endsWith(SUFFIX_CLASS)) {
            pathStr = pathStr.substring(0, pathStr.length() - SUFFIX_CLASS.length());
        }
        return pathStr;
    }
}
