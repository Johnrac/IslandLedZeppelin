package com.javarush.island.pukhov.api.annotation;

import com.javarush.island.pukhov.constant.ConstantsDefault;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Default {

    String icon() default ConstantsDefault.OBJECT_ICON;
}
