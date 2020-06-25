plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
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

kapt {
    correctErrorTypes = true
}