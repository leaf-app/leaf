plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion = Dependencies.Versions.buildTools
    defaultConfig {
        minSdkVersion(Dependencies.Versions.minSdk)
        targetSdkVersion(Dependencies.Versions.compileSdk)

        buildConfigField("String", "VK_API_VERSION", "5.110")
    }
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
    api(Dependencies.Libs.kotlinStd)

    //Libraries
    api(Dependencies.Libs.core)
    api(Dependencies.Libs.firebaseConfig)
}