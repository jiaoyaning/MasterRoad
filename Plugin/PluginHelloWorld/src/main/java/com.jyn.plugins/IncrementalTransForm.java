package com.jyn.plugins;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.build.gradle.internal.tasks.Workers;
import com.android.ide.common.internal.WaitableExecutor;
import com.android.ide.common.workers.ExecutorServiceAdapter;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/*
 * 增量编译
 *
 * Transform的优化：增量与并发 部分
 * https://blog.csdn.net/weixin_34413802/article/details/89543858
 * https://github.com/Leaking/Hunter/blob/master/hunter-transform/src/main/java/com/quinn/hunter/transform/HunterTransform.java
 * https://github.com/Leifzhang/AndroidAutoTrack/blob/master/Plugin/BasePlugin/src/main/java/com/kronos/plugin/base/BaseTransform.kt
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

    /**
     * 开启了增量编译之后需要检查每个文件的Status,然后根据这个文件的Status进行不同的操作.
     *
     * 具体的Status如下:
     * NOTCHANGED:     当前文件不需要处理,连复制操作也不用
     * ADDED:          正常处理,输出给下一个任务
     * CHANGED:        正常处理,输出给下一个任务
     * REMOVED:        移除outputProvider获取路径对应的文件
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.println("--- transform 开始");

        //当前是否是增量编译，clean之后第一次运行必然是false
        boolean isIncremental = transformInvocation.isIncremental();
        System.out.println("---- IncrementalTransForm 当前是否是增量编译 : " + isIncremental);

        //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        Collection<TransformInput> inputs = transformInvocation.getInputs();

        //引用型输入，无需输出。
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();

        //OutputProvider管理输出路径，如果消费型输入为空，你会发现OutputProvider == null
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        if (!isIncremental) {
            outputProvider.deleteAll();
        }

        WaitableExecutor mWaitableExecutor = WaitableExecutor.useGlobalSharedThreadPool();

        inputs.forEach(input -> {
            // 对文件夹进行遍历，里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
            input.getDirectoryInputs().forEach(directoryInput ->
                    mWaitableExecutor.execute(() -> {
                        processDirectoryInput(directoryInput, outputProvider, isIncremental);
                        return null;
                    }));

            // 对类型为jar文件的input进行遍历
            input.getJarInputs().forEach(jarInput ->
                    mWaitableExecutor.execute(() -> {
                        processJarInput(jarInput, outputProvider, isIncremental);
                        return null;
                    }));
        });

        //等待所有任务结束
        try {
            mWaitableExecutor.waitForTasksWithQuickFail(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("--- transform 结束 历时:" + (System.currentTimeMillis() - startTime) + "ms");
    }

    /*
     * 处理jar
     * 将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
     */
    void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider, boolean isIncremental) {
        Status status = jarInput.getStatus();
        System.out.println("---- jar input :" + jarInput.getFile().getAbsolutePath());

        File dest = outputProvider.getContentLocation(
                jarInput.getFile().getAbsolutePath(),
                jarInput.getContentTypes(),
                jarInput.getScopes(),
                Format.JAR);

        if (isIncremental) {
            switch (status) {
                case NOTCHANGED: //当前文件不需要处理,连复制操作也不用
                    break;
                case ADDED:
                case CHANGED:
                    transformJar(jarInput.getFile(), dest);
                    break;
                case REMOVED:
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
        System.out.println("---- jar output :" + dest.getAbsolutePath());
    }

    /*
     * 处理源码文件
     * 将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
     */
    void processDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider, boolean isIncremental) {
        // 获取输出目录
        System.out.println("---- directory input :" + directoryInput.getFile().getAbsolutePath());

        File dest = outputProvider.getContentLocation(
                directoryInput.getName(),
                directoryInput.getContentTypes(),
                directoryInput.getScopes(),
                Format.DIRECTORY);
        try {
            FileUtils.forceMkdir(dest); //强制创建文件目录，如果文件存在，会抛出异
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isIncremental) {
            //获取输入文件的路径
            String srcDirPath = directoryInput.getFile().getAbsolutePath();
            //获取输出文件的路径
            String destDirPath = dest.getAbsolutePath();
            //获取所有改变文件的状态
            Map<File, Status> fileStatusMap = directoryInput.getChangedFiles();
            fileStatusMap.forEach((file, status) -> {

                String destFilePath = file.getAbsolutePath().replace(srcDirPath, destDirPath);
                File destFile = new File(destFilePath);

                switch (status) {
                    case NOTCHANGED:
                        break;
                    case ADDED:
                    case CHANGED:
                        try {
                            FileUtils.touch(destFile);//创建文件，如果文件存在则更新时间；如果不存在，创建一个空文件
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        transformSingleFile(file, destFile); //拷贝单个文件
                        break;
                    case REMOVED:
                        if (destFile.exists()) {
                            try {
                                FileUtils.forceDelete(destFile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            });
        } else {
            // 拷贝整个文件夹
            transformDirectory(directoryInput.getFile(), dest);
        }
    }

    // 同基本操作一样，直接copy到输出路径
    private void transformJar(File srcJar, File destJar) {
        try {
            FileUtils.copyFile(srcJar, destJar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //拷贝单个文件
    void transformSingleFile(File inputFile, File destFile) {
        try {
            FileUtils.copyFile(inputFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //拷贝文件夹
    void transformDirectory(File directoryInputFile, File dest) {
        try {
            FileUtils.copyDirectory(directoryInputFile, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
