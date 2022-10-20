/**
 * 添加资源路径依赖
 * 调用示范：ext.addRH.call(repositories)
 */
fun addRH(handler: RepositoryHandler) {
    handler.apply {
//        mavenCentral()
        mavenLocal() //本地插件上传仓库
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
}
extra["addRH"] = this::addRH

extra["addRH2"] = { handler: RepositoryHandler ->
    handler.apply {
//        mavenCentral()
        mavenLocal() //本地插件上传仓库
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
}

/**
 * 添加依赖
 */
fun addDep(handler: DependencyHandler?, conf: String, map: Map<String, Any>?) {
    handler?.apply {
        map?.values?.map { it.toString() }?.map {
            add(if (it.contains("compiler")) "kapt" else conf, it)
        }
    }
}
extra["addDep"] = this::addDep

/**
 * 调用示范：ext.testFun.invoke(1,2)
 */
extra["testFun"] = { a: Int, b: Int -> a + b }

/**
 * 打印全部Task
 * 调用示范：ext.printlnAllTask.invoke()
 */
extra["printlnAllTask"] = {
    println()
    gradle.afterProject {
        getAllTasks(true).forEach {
            println(">> ${it.key.name}")
            it.value.forEach { task -> println("==> ${task.name} : ${task.javaClass.name}") }
        }
    }
    println()
}

/**
 * 去掉一些测试的task
 */
extra["removeTestTask"] = {
    gradle.taskGraph.whenReady {
        allTasks.forEach { task ->
            task.enabled = !task.name.contains("Test") || task.name != "mockableAndroidJar"
        }
    }
}

extra["getLogMsg"] = { cls: Class<*> ->
    val ste = Thread.currentThread().stackTrace.first { it.className == cls.name }
    "${ste.fileName}:${ste.lineNumber}"
}