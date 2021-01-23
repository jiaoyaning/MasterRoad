package com.jyn.java_kotlin.Reflect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Retention Retention(保留) 用来标记自定义注解的有效范围，他的取值有以下三种：
 * RetentionPolicy.SOURCE:      只在源代码中保留 一般都是用来增加代码的理解性或者帮助代码检查之类的，比如我们的Override;
 * RetentionPolicy.CLASS:       默认的选择，能把注解保留到编译后的字节码class文件中，仅仅到字节码文件中，运行时是无法得到的；
 * RetentionPolicy.RUNTIME:     注解不仅 能保留到class字节码文件中，还能在运行通过反射获取到，这也是我们最常用的。
 *
 * @Target 指定Annotation用于修饰哪些程序元素。
 * @Target 也包含一个名为”value“的成员变量，该value成员变量类型为ElementType[ ]，ElementType为枚举类型，值有如下几个：
 * ElementType.TYPE：            类、接口或枚举类型
 * ElementType.FIELD：           成员变量
 * ElementType.METHOD：          方法
 * ElementType.PARAMETER：       参数
 * ElementType.CONSTRUCTOR：     构造器
 * ElementType.LOCAL_VARIABLE：  局部变量
 * ElementType.ANNOTATION_TYPE： 注解
 * ElementType.PACKAGE：         包
 *
 * @Documented 表示可以在javadoc中找到
 *
 * @Inherited 表示注解里的内容可以被子类继承
 */

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface TestAnnotation {
    String value() default "测试注解的默认value";
}
