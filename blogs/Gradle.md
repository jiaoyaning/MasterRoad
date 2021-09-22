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