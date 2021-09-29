package com.jyn.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //运行时还需要用到
@Target(ElementType.FIELD)
public @interface ReflectBindView {
    int value();
}
