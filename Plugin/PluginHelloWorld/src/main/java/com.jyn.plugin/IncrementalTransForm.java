package com.jyn.plugin;

import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/*
 * Transform的优化：增量与并发 部分
 * https://blog.csdn.net/weixin_34413802/article/details/89543858
 * https://github.com/Leaking/Hunter/blob/master/hunter-transform/src/main/java/com/quinn/hunter/transform/HunterTransform.java
 * https://github.com/Leifzhang/AndroidAutoTrack/blob/master/Plugin/BasePlugin/src/main/java/com/kronos/plugin/base/BaseTransform.kt
 *
 * 手把手教大家用Transform API和ASM实现一个防快速点击案例
 * https://mp.weixin.qq.com/s?__biz=MzUzOTk2MDUxMw==&mid=2247484076&idx=1&sn=e06a95632487c5d3975ecdfce8ef5295&chksm=fac13702cdb6be14325e125a269d1db20335291867d2380f1e0dbabf887b324899fdb87294ec#rd
 *
 *
 */
class IncrementalTransForm extends Transform {

    Project project;

    public IncrementalTransForm(Project project) {
        this.project = project;
        System.out.println("--- 创建一个 Transform :" + this.getName());
    }

    @Override
    public String getName() {
        return "IncrementalTransForm";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    /*
     * 即使返回 true 也并非每次编译过程都是支持增量的，毕竟一次clean build完全没有增量的基础
     */
    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws IOException {
        //当前是否是增量编译
        boolean isIncremental = transformInvocation.isIncremental();
        System.out.println("---- IncrementalTransForm 当前是否是增量编译 : " + isIncremental);

        //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        Collection<TransformInput> inputs = transformInvocation.getInputs();

        //引用型输入，无需输出。
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();


        //OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        /*
         * 如果非增量，则清空旧的输出内容
         * 如果是增量，则要检查每个文件的Status，Status分四种，并且对这四种文件的操作也不尽相同
         *    NOTCHANGED: 当前文件不需处理，甚至复制操作都不用；
         *    ADDED、CHANGED: 正常处理，输出给下一个任务；
         *    REMOVED: 移除outputProvider获取路径对应的文件。
         */
        if (!isIncremental) {
            outputProvider.deleteAll();
        }

        inputs.forEach(input -> {

            // 对文件夹进行遍历，里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
            input.getDirectoryInputs().forEach(directoryInput -> {
                File dest = outputProvider.getContentLocation(
                        directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY);



            });

            // 对类型为jar文件的input进行遍历
            input.getJarInputs().forEach(jarInput -> {
                Status status = jarInput.getStatus();
                File dest = outputProvider.getContentLocation(
                        jarInput.getName(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                if (isIncremental) {
                    switch (status) {
                        case NOTCHANGED: //当前文件不需处理，甚至复制操作都不用；
                            break;
                        case ADDED: //需要处理
                        case CHANGED: //需要处理
                            transformJar(jarInput.getFile(), dest);
                            break;
                        case REMOVED: //移除outputProvider获取路径对应的文件
                            if (dest.exists()) {
                                try {
                                    FileUtils.forceDelete(dest);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                    }
                } else {
                    transformJar(jarInput.getFile(), dest);
                }
            });
        });
    }


    // 同基本操作一样，直接copy到输出路径
    private void transformJar(File srcJar, File destJar) {
        try {
            FileUtils.copyFile(srcJar, destJar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 扫描所有的jar文件
     */
    public HashSet<String> scanJarFile(File jarFile) throws IOException {
        HashSet<String> hashSet = new HashSet<>();
        JarFile file = new JarFile(jarFile);
        Enumeration<JarEntry> enumeration = file.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement();
            String entryName = jarEntry.getName();
            if (entryName.endsWith(".class")) {
                hashSet.add(entryName);
            }
        }
        file.close();
        return hashSet;
    }

}
