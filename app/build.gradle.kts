import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

val keysFile = rootProject.file("keys.properties")
val keysProps = Properties()
keysProps.load(FileInputStream(keysFile))

android {
    signingConfigs {
        create("default") {
            storeFile = rootProject.file("keys.jks")
            keyAlias = "leaf"
            storePassword = keysProps.getProperty("STORE_PASSWORD")
            keyPassword = keysProps.getProperty("KEY_PASSWORD")
        }
    }
    compileSdkVersion(30)
    buildToolsVersion = "30.0.0"

    defaultConfig {
        applicationId = "ru.dzgeorgy.leaf"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("default")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //Kotlin standard library
    implementation(Dependencies.Libs.kotlinStd)

    //Libraries
    implementation(Dependencies.Libs.appcompat)
    implementation(Dependencies.Libs.core)
    implementation(Dependencies.Libs.materialComponents)

    //Firebase
    implementation(Dependencies.Libs.firebaseAnalytics)

    //Test libraries
    testImplementation(Dependencies.TestLibs.junit)
    androidTestImplementation(Dependencies.TestLibs.test)
    androidTestImplementation(Dependencies.TestLibs.espresso)
}