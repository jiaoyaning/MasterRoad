/**
 * 添加资源路径依赖
 */
fun addRH(handler: RepositoryHandler) {
    handler.apply {
        mavenCentral()
        val mavenLocal = mavenLocal() //本地插件上传仓库
//        println("mavenLocal地址 : ${mavenLocal.url}")
        mavenLocal.url
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
 * 添加依赖
 */
fun addDep(handler: DependencyHandler?, conf: String, map: Map<String, Any>?) {
    handler?.apply {
        map?.values?.map {
            add(if (it.toString().contains("compiler")) "kapt" else conf, it)
        }
    }
}

extra["addDep"] = this::addDep

/**
 * 调用示范：ext.testFun.invoke(1,2)
 */
extra["testFun"] = { a: Int, b: Int ->
    println("--> testFun a+b=${a + b}")
}

/**
 * 打印全部Task
 * 调用示范：ext.printlnAllTask.invoke()
 */
extra["printlnAllTask"] = {
    println()
    gradle.afterProject {
        getAllTasks(true).forEach {
            println(">> ${it.key.name}")
            it.value.forEach { task ->
                println("==> ${task.name} : ${task.javaClass.name}")
            }
        }
    }

    println()
}

/**
 * 去掉一些测试的task
 */
extra["removeTestTask"] = {
    gradle.taskGraph.whenReady {
        tasks.forEach { task ->
            if (task.name.contains("Test")) {
                task.enabled = false
            } else if (task.name == "mockableAndroidJar") {
                task.enabled = false
            }
        }
    }
}
