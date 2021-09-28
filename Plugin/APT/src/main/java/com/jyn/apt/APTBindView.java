package com.jyn.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE) //只有编译时用到
@Target(ElementType.FIELD)
public @interface APTBindView {
    int id();
}
