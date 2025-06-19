package com.javarush.island.pukhov.util;

import com.javarush.island.pukhov.exception.ConfigurationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class AnnotationProcessor {

    private AnnotationProcessor() {
    }

    /**
     * Returns a map with attribute names and their values
     * @param annotationClass  research annotation
     * @param clazz  annotated class
     * @return attributes class
     */
    public static Map<String, Object> getMapAttributesFor(Class<? extends Annotation> annotationClass, Class<?> clazz) {
        Map<String, Object> mapFields = new HashMap<>();
        if (clazz.isAnnotationPresent(annotationClass)) {
            Annotation annotation = clazz.getAnnotation(annotationClass);
            Method[] methods = annotationClass.getDeclaredMethods();
            for (Method method : methods) {
                try {
                    mapFields.put(method.getName(), method.invoke(annotation));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ConfigurationException(e);
                }
            }
        }
        return mapFields;
    }
}
