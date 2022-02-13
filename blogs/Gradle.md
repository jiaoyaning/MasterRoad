# 各文件作用
根目录下的 `gradlew(w是wrapper)` 才是真正的 `gradle wrapper`，
`gradle-wrapper.properties`文件只是一个配置类，`gradlew`意思是 `gradle wrapper 即gradle的包装类`，

> 使用`gradlew`就可以避免全局配置`gradle`命令，
> 如果所配置版本的`gradle`不存在于系统中时，`wrapper`会去`Gradle`官方下载指定版本(即 gradle-wrapper.properties文件中所配置)的gradle来执行build。
> 使用这样的好处是，即使在不同成员所全局安装的gradle版本不同时或者是没配置全局gradle时，也能成功编译

1. gradle-wrapper.jar
   > 负责下载指定版本的Gradle
2. gradle-wrapper.properties
   > Gradle Wrapper运行时的配置项，决定Gradle Wrapper build下载的Gradle版本。
3. gradlew,gradlew.bat
   > 负责执行Gradle Wrapper的脚本。

[参考](https://matthung0807.blogspot.com/2019/11/gradle-gradle-wrapper.html)

# 生命周期
[Android Gradle学习(七)：Gradle构建生命周期](https://www.jianshu.com/p/2e19268bf387)
[](https://juejin.cn/post/6844903508844478471#heading-8)

1. 初始化阶段
> 会去读取根工程中 setting.gradle 中的 include 信息，决定有哪几个工程加入构建，创建 project 实例。
> 比如下面有三个工程： include ‘:app’, ‘:lib1’, ‘:lib2 。

2. 配置阶段
> 会去执行所有工程的 build.gradle 脚本，配置 project对象，一个对象由多个任务组成， 此阶段也会去创建、配置task及相关信息。

3. 运行阶段
> 根据gradle命令传递过来的task名称，执行相关依赖任务。Task 的 Action 会在这个阶段执行。

# 执行顺序
1. 首先执行项目目录下面的setting.gradle
2. 然后执行项目目录下面的build.gradle
3. 然后根据setting.gradle中的配置顺序逆序执行module的build.gradle(即后写的先执行)


# Task

## 默认Task
1. copy
```groovy
task 任务的名字 (type: Copy) {
    //action 
}
```

Api 介绍
```groovy
//数据源目录，多个目录
public AbstractCopyTask from(Object... sourcePaths)  

//目标目录，单一
public AbstractCopyTask into(Object destDir) 

//过滤文件 包含
public AbstractCopyTask include(String... includes)

//过滤文件 排除
public AbstractCopyTask exclude(String... excludes)

//重新命名，老名字 新名字
public AbstractCopyTask rename(String sourceRegEx, String replaceWith)

//删除文件 Project 接口
boolean delete(Object... paths);
```

demo
```groovy
//复制图片：单一数据源
task copyImage(type: Copy) {
   from 'C:\\Users\\yiba_zyj\\Desktop\\gradle\\copy'
   into 'C:\\Users\\yiba_zyj\\Desktop'
}

//复制图片：多个数据源
task copyImage(type: Copy) {
   from 'C:\\Users\\yiba_zyj\\Desktop\\gradle\\copy' , 
           'C:\\Users\\yiba_zyj\\Desktop\\gradle\\copy'
   into 'C:\\Users\\yiba_zyj\\Desktop'
}

//复制图片：过滤文件
task copyImage(type: Copy) {
   from 'C:\\Users\\yiba_zyj\\Desktop\\gradle\\copy'
   into 'C:\\Users\\yiba_zyj\\Desktop'
   include "*.jpg"
}

//复制文件：过滤文件，重命名
task copyImage(type: Copy) {
   from 'C:\\Users\\yiba_zyj\\Desktop\\gradle\\copy'
   into 'C:\\Users\\yiba_zyj\\Desktop'
   include "*.jpg"
   exclude "image1.jpg"
   rename("image2.jpg","123.jpg")
}
```

2.  delete
```groovy
task deleteFile(type: Delete) {
    //删除Android 更目录的aaa 文件
    delete '../aaa'  
}

task deleteFile(type: Delete) {
   //删除系统桌面 delete 
   delete "C:\\Users\\yiba_zyj\\Desktop\\gradle\\delete"
}
```

## 