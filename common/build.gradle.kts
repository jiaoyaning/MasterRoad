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
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(29)
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:1.2.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime:2.3.1")
    implementation("androidx.activity:activity-ktx:1.2.2")
    implementation("androidx.core:core-ktx:1.5.0")

    implementation("com.google.dagger:dagger-android:2.35.1")
    annotationProcessor("com.google.dagger:dagger-android-processor:2.15")

    implementation("com.alibaba:arouter-api:1.5.0")
    implementation("com.tencent:mmkv-static:1.2.7")
    api("com.apkfuns.logutils:library:1.7.5")

    //hilt
    implementation("com.google.dagger:hilt-android:2.28-alpha")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01")
    kapt("com.google.dagger:hilt-android-compiler:2.28-alpha")
    kapt("androidx.hilt:hilt-compiler:1.0.0-alpha01")
}
