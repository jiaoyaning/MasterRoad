package com.jyn.plugin;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/*
 * Transform 是 Android 官方提供给开发者在项目构建阶段由 class 到 dex 转换期间修改class文件的一套api。
 *
 * Gradle 学习之 Android 插件的 Transform API
 * https://juejin.cn/post/6844903891138674696
 *
 * 每个 Transform 其实都是一个 Gradle的Task，Android 编译器中的 TaskManager 会将每个 Transform 串联起来。
 * 第一个 Transform 接收来自 javac 编译的结果，以及拉取到本地的第三方依赖和 resource 资源。
 * 这些编译的中间产物在 Transform 链上流动，每个 Transform 节点都可以对 class 进行处理再传递到下一个 Transform 。
 * 我们自定义的 Transform 会插入到链的最前面，可以在 TaskManager 类的 createPostCompilationTasks 方法中找到相关逻辑
 */
class HelloWorldTransform extends Transform {
    Project project;

    public HelloWorldTransform(Project project) {
        this.project = project;
        System.out.println("--- 创建一个 Transform :" + this.getName());
    }

    /*
     * Transform的名称，也对应了该 Transform 所代表的 Task 名称
     *
     * 类似：transformClassesWith + getName() + ForXXX
     * 这里应该是：transformClassesWithHelloWorldTransformForXXX
     */
    @Override
    public String getName() {
        return "HelloWorldTransform";
    }

    /*
     * Transform处理文件的类型,TransformManager中所定义
     *
     * 1、CONTENT_CLASS       javac 编译成的 class 文件，可能是jar包也可能是目录
     * 2、CONTENT_RESOURCES   标准的java资源
     *
     * 其中，很多类型是不允许自定义 Transform 来处理的，我们常使用 CONTENT_CLASS 来操作 Class 文件
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /*
     * Transform的作用域,TransformManager中定义了几种范围
     *
     * SCOPE_FULL_PROJECT     代表所有 Project (常用)
     *
     * 确定了 InputTypes 和 Scope 后就确定了该自定义 Transform 需要处理的资源流。
     * 比如 CONTENT_CLASS 和 SCOPE_FULL_PROJECT 表示了所有项目中 java 编译成的 class 组成的资源流。
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    /*
     * 是否支持增量编译，增量编译就是如果第二次编译相应的task没有改变，那么就直接跳过，节省时间
     *
     * 注意: 即使某些情况下返回了 true ，但是在运行时它还是会返回 false
     *
     * https://www.cnblogs.com/davenkin/p/3418260.html
     */
    @Override
    public boolean isIncremental() {
        return true;
    }

    /*
     * 最主要的方法，这里对文件或jar进行处理，进行代码的插入
     *
     * TransformInput:     对输入的class文件转变成目标字节码文件，TransformInput就是这些输入文件的抽象。
     * 目前它包含DirectoryInput集合与JarInput集合， jar 和目录格式
     * DirectoryInput:     源码方式参与项目编译的所有目录结构及其目录下的源文件。
     * JarInput:           Jar包方式参与项目编译的所有本地jar或远程jar包
     *
     * referencedInputs:   集合仅供参考，不应进行转换，它是受 getReferencedScopes 方法控制
     * outputProvider:     用来获取输出目录的，我们要将操作后的文件复制到输出目录中
     */
    @Override
    public void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental);
    }

    /**
     * TransformInput 接口 {@link TransformInput}
     * 所谓 Transform 就是对输入的 class 文件转变成目标字节码文件，
     * 而TransformInput 就是这些输入文件的抽象。目前它包括两部分：DirectoryInput 集合与 JarInput 集合。
     * <p>
     * DirectoryInput 代表以源码方式参与项目编译的所有目录结构及其目录下的源码文件，
     * 可以借助于它来修改输出文件的目录结构以及目标字节码文件。
     * <p>
     * JarInput 代表以 jar 包方式参与项目编译的所有本地 jar 包或远程 jar 包，可以借助它来动态添加 jar 包。
     * <p>
     * --------------------------------
     * <p>
     * TransformOutputProvider 接口 {@link TransformOutputProvider}
     * 可调用 getContentLocation 获取输出目录
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        long startTime = System.currentTimeMillis();

        System.out.println("--- transform 开始");

        Collection<TransformInput> invocationInputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        invocationInputs.forEach(transformInput -> {
            // 对文件夹进行遍历，里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
            transformInput.getDirectoryInputs().forEach(directoryInput -> {
                // 获取输出目录
                System.out.println("---- directory input :" + directoryInput.getFile().getAbsolutePath());

                File contentLocation = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);

                System.out.println("---- directory output :" + contentLocation.getAbsolutePath());

                // 将input的目录复制到output指定目录
                try {
                    FileUtils.copyDirectory(directoryInput.getFile(), contentLocation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // 对类型为jar文件的input进行遍历
            transformInput.getJarInputs().forEach(jarInput -> {

                System.out.println("---- jar input :" + jarInput.getFile().getAbsolutePath());
                // 重命名输出文件（同目录copyFile会冲突）
                String newName = jarInput.getName().replace(".jar", "") + DigestUtils.md5Hex(jarInput.getName());

                File contentLocation = outputProvider.getContentLocation(newName,
                        jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);

                System.out.println("---- jar output :" + contentLocation.getAbsolutePath());
                try {
                    FileUtils.copyFile(jarInput.getFile(), contentLocation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        });
        System.out.println("--- transform 结束 历时:" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
