/**
 *
 */
fun addRH(handler: RepositoryHandler) {
    println("\n============== config.gradle.kts addRepos 开始 ================")
    handler.apply {
        mavenCentral()
        mavenLocal() //本地插件上传仓库
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
    println("============== config.gradle.kts addRepos 结束 ================\n")
}

extra["addRH"] = this::addRH

