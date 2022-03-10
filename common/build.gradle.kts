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
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
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
//    buildFeatures.dataBinding = true
//    buildFeatures.viewBinding = true

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

/*
 * https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources
 */
dependencies {
    val test = rootProject.ext.get("test") as Map<*, *>
    val androidx = rootProject.ext.get("androidx") as Map<*, *>
    val utils = rootProject.ext.get("utils") as Map<*, *>

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    testImplementation(test["junit"]!!)
    androidTestImplementation(test["ext-junit"]!!)
    androidTestImplementation(test["espresso-core"]!!)

    implementation(androidx["appcompat"]!!)
    implementation(androidx["viewmodel-ktx"]!!)
    implementation(androidx["livedata-ktx"]!!)
    implementation(androidx["runtime-ktx"]!!)
    implementation(androidx["lifecycle-process"]!!)
    implementation(androidx["activity-ktx"]!!)
    implementation(androidx["core-ktx"]!!)

    implementation("com.google.dagger:dagger-android:2.35.1")
    annotationProcessor("com.google.dagger:dagger-android-processor:2.15")

    implementation(utils["arouter"]!!)
    implementation(utils["mmkv"]!!)
    api(utils["logutils"]!!)

    debugImplementation(utils["leakcanary"]!!)

    //hilt
    implementation(androidx["hilt"]!!)
    implementation(androidx["hilt-viewmodel"]!!)
    kapt(androidx["hilt-compiler"]!!)
    kapt(androidx["hilt-compiler2"]!!)
}
