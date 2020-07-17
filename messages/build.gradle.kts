plugins {
    //Android + Kotlin
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")

    //Other
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion = Dependencies.Versions.buildTools
    defaultConfig {
        minSdkVersion(Dependencies.Versions.minSdk)
        targetSdkVersion(Dependencies.Versions.compileSdk)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Dependencies.Versions.jvm
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    //Projects
    implementation(project(":ui"))

    //Libraries
    implementation(Dependencies.Libs.hilt)
    implementation(Dependencies.Libs.hiltAndroid)

    //Annotation processors
    kapt(Dependencies.AnnotationProcessors.hilt)
    kapt(Dependencies.AnnotationProcessors.hiltAndroid)
}