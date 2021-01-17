package com.jyn.plugin;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.gradle.api.Project;

import java.io.IOException;
import java.util.Set;

/**
 * Transform 是 Android 官方提供给开发者在项目构建阶段由 class 到 dex 转换期间修改class文件的一套api。
 */
class HelloWorldTransform extends Transform {
    Project project;

    public HelloWorldTransform(Project project) {
        this.project = project;
    }

    /**
     * Transform的名称，但是这里并不是真正的名称，真正的名称还需要进行拼接
     */
    @Override
    public String getName() {
        return "HelloWorldTransform";
    }

    /**
     * Transform处理文件的类型
     * <p>
     * 1、CLASSES 表示要处理编译后的字节码，可能是jar包也可能是目录
     * 2、RESOURCES表示处理标准的java资源
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * Transform的作用域:
     * <p>
     * PROJECT                只处理当前的文件
     * SUB_PROJECTS	          只处理子项目
     * EXTERNAL_LIBRARIES	  只处理外部的依赖库
     * TESTED_CODE	          测试代码
     * PROVIDED_ONLY	      只处理本地或远程以provided形式引入的依赖库
     * PROJECT_LOCAL_DEPS (Deprecated，使用EXTERNAL_LIBRARIES)	       只处理当前项目的本地依赖，例如jar、aar
     * SUB_PROJECTS_LOCAL_DEPS (Deprecated，使用EXTERNAL_LIBRARIES)	   只处理子项目的本地依赖。
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    /**
     * 是否支持增量编译，增量编译就是如果第二次编译相应的task没有改变，那么就直接跳过，节省时间
     *
     * https://www.cnblogs.com/davenkin/p/3418260.html
     */
    @Override
    public boolean isIncremental() {
        return false;
    }

    /**
     * 最主要的方法，这里对文件或jar进行处理，进行代码的插入
     *
     * TransformInput:        对输入的class文件转变成目标字节码文件，TransformInput就是这些输入文件的抽象。
     * 目前它包含DirectoryInput集合与JarInput集合。
     *
     * DirectoryInput:        源码方式参与项目编译的所有目录结构及其目录下的源文件。
     * JarInput:              Jar包方式参与项目编译的所有本地jar或远程jar包
     * TransformOutProvider:  通过这个类来获取输出路径。
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
    }
}
