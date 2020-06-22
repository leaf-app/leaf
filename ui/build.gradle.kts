plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion = Dependencies.Versions.buildTools
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Dependencies.Versions.jvm
    }
}

dependencies {

    //Kotlin standard library
    implementation(Dependencies.Libs.kotlinStd)

    //Libraries
    api(Dependencies.Libs.activity)
    api(Dependencies.Libs.appcompat)
    api(Dependencies.Libs.constraintLayout)
    api(Dependencies.Libs.core)
    api(Dependencies.Libs.fragment)
    api(Dependencies.Libs.lifecycleCommon)
    api(Dependencies.Libs.lifecycleLiveData)
    api(Dependencies.Libs.lifecycleViewModel)
    api(Dependencies.Libs.materialComponents)
    api(Dependencies.Libs.navigation)
    api(Dependencies.Libs.navigationUi)
}