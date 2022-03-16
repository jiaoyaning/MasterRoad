/**
 * 添加资源路径依赖
 */
fun addRH(handler: RepositoryHandler) {
    handler.apply {
        mavenCentral()
        mavenLocal() //本地插件上传仓库
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
}

/**
 * 添加资源路径依赖
 * 调用示范：ext.addRH.call(repositories)
 */
extra["addRH"] = this::addRH

/**
 * 打印全部Task
 * 调用示范：ext.printlnAllTask.invoke()
 */
extra["printlnAllTask"] = {
    println()
    project.getAllTasks(true).forEach {
        println("==> ${it.key.name}")
        it.value.forEach { task ->
            println("====> ${task.name} : ${task.javaClass.name}")
        }
    }
    println()
}

/**
 * 调用示范：ext.testFun.invoke(1,2)
 */
extra["testFun"] = { a: Int, b: Int ->
    println("=========================> testFun a+b=${a + b}")
}