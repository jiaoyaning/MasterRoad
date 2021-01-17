package com.jyn.plugin;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.AppPlugin;  //这是com.android.application所属的Plugin
import com.android.build.gradle.LibraryPlugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginContainer;

import java.util.Iterator;

/**
 * Android Gradle Plugin插件开发——基础
 * https://blog.csdn.net/guy_guy/article/details/80914600
 */
class HelloWorld implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        System.out.println("- 开始加载插件 HelloWorldPlugin");

        /*
         * 判断是否拥有 com.android.application 或者 com.android.library插件
         * AppPlugin 和 LibraryPlugin 可以点进去可看到，也都是一个Plugin
         */
        if (target.getPlugins().hasPlugin(AppPlugin.class)) {
            System.out.println("-- 这是一个Application 名字是:" + target.getName());
        } else if (target.getPlugins().hasPlugin(LibraryPlugin.class)) {
            System.out.println("-- 这是一个Model 名字是:" + target.getName());
        }

        //判断是否有Android{}闭包
        if (target.hasProperty("android")) {
            System.out.println("-- 该Project拥有 android{} 闭包");
            AppExtension appExtension = target.getExtensions().getByType(AppExtension.class);
            System.out.println("-- 开始注册 Transform ");
            appExtension.registerTransform(new HelloWorldTransform(target));
            System.out.println("-- Transform 注册成功");
        }
    }
}