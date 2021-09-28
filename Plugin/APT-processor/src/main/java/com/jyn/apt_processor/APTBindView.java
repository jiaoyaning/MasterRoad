package com.jyn.apt_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在主项目和注解处理库中都需要用到这个该类
 * 最好的做法是，把其单独抽取成一个库，分别给APP主项目，和注解处理库依赖，这里图简单，放到了一起
 */
@Retention(RetentionPolicy.SOURCE) //只有编译时用到
@Target(ElementType.FIELD)
public @interface APTBindView {
    int id();
}
