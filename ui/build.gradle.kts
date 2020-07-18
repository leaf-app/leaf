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
    buildFeatures.dataBinding = true
}

dependencies {
    //Projects
    api(project(":core"))

    //Libraries
    api(Dependencies.Libs.appcompat)
    api(Dependencies.Libs.coil)
    api(Dependencies.Libs.constraintLayout)
    api(Dependencies.Libs.fragment)
    implementation(Dependencies.Libs.hilt)
    api(Dependencies.Libs.hiltAndroid)
    api(Dependencies.Libs.lifecycleCommon)
    api(Dependencies.Libs.lifecycleLiveData)
    api(Dependencies.Libs.lifecycleViewModel)
    api(Dependencies.Libs.materialComponents)
    api(Dependencies.Libs.navigation)
    api(Dependencies.Libs.navigationUi)

    //Annotation processors
    kapt(Dependencies.AnnotationProcessors.hilt)
    kapt(Dependencies.AnnotationProcessors.hiltAndroid)
}