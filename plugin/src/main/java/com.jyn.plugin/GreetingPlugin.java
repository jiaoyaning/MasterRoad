package com.jyn.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

class GreetingPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        System.out.println("----- 加载插件 ------" + target.getName());
    }
}