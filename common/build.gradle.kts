import org.jetbrains.kotlin.config.KotlinCompilerVersion

/*
 * Gradle Kotlin DSL
 * https://mp.weixin.qq.com/s/MTbTecDYcYpRkoqc6pC_2w
 *
 * 官方文档 (Migrating build logic from Groovy to Kotlin)
 * https://docs.gradle.org/nightly/userguide/migrating_from_groovy_to_kotlin_dsl.html
 *
 * Gradle Kotlin DSL (不错)
 * https://blog.csdn.net/Utzw0p0985/article/details/107551455
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
//    id("kotlin-android-extensions")
    /*
     * kapt 中文官网
     * https://www.kotlincn.net/docs/reference/kapt.html
     */
    id("kotlin-kapt")
}

val app = rootProject.ext

android {
    compileSdk = app.get("compileSdkVersion") as Int
    buildToolsVersion = app.get("buildToolsVersion") as String

    defaultConfig {
        minSdk = app.get("minSdkVersion") as Int
        targetSdk = app.get("targetSdkVersion") as Int
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.name)
        }
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

/*
 * https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources
 */
dependencies {
    val test = app.get("test") as Map<*, *>
    val androidTest = app.get("androidTest") as Map<*, *>
    val utils = app.get("utils") as Map<*, *>
    val androidx = app.get("androidx") as Map<*, *>

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    testImplementation(test["junit"]!!)
    androidTestImplementation(androidTest["ext-junit"]!!)
    androidTestImplementation(androidTest["espresso-core"]!!)

    implementation(androidx["appcompat"]!!)
    implementation(androidx["viewmodel-ktx"]!!)
    implementation(androidx["livedata-ktx"]!!)
    implementation(androidx["runtime-ktx"]!!)
    implementation(androidx["lifecycle-process"]!!)
    implementation(androidx["activity-ktx"]!!)
    implementation(androidx["core-ktx"]!!)

    implementation(androidx["hilt"]!!)

    implementation(utils["arouter"]!!)
    implementation(utils["mmkv"]!!)
    implementation(utils["logutils"]!!)

    debugApi("com.squareup.leakcanary:leakcanary-android:2.8.1") //https://github.com/square/leakcanary
    debugApi("com.github.markzhai:blockcanary-android:1.5.0") //https://github.com/markzhai/AndroidPerformanceMonitor
}
