import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("eu.davidea.grabver")
    id("com.google.firebase.appdistribution")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val keysFile = rootProject.file("keys.properties")
val keysProps = Properties()
keysProps.load(FileInputStream(keysFile))

versioning {
    major = 0
    minor = 1
    preRelease = "alpha"
}

android {
    signingConfigs {
        create("default") {
            storeFile = rootProject.file("keys.jks")
            keyAlias = "leaf"
            storePassword = keysProps.getProperty("STORE_PASSWORD")
            keyPassword = keysProps.getProperty("KEY_PASSWORD")
        }
    }
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion = Dependencies.Versions.buildTools

    defaultConfig {
        applicationId = "ru.dzgeorgy.leaf"
        minSdkVersion(Dependencies.Versions.minSdk)
        targetSdkVersion(Dependencies.Versions.compileSdk)
        versionCode = versioning.code
        versionName = versioning.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("default")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("default")
            firebaseAppDistribution {
                releaseNotesFile = rootDir.absolutePath + "/log.txt"
                groupsFile = rootDir.absolutePath + "/group.txt"
                serviceCredentialsFile =
                    rootDir.absolutePath + "/ru-dzgeorgy-leaf-efa8ee88d103.json"
            }
        }
        getByName("debug") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            versionNameSuffix = versioning.build.toString()
            signingConfig = signingConfigs.getByName("default")
        }
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
    implementation(project(":auth"))

    //Kotlin standard library
    implementation(Dependencies.Libs.kotlinStd)

    //Libraries
    implementation(Dependencies.Libs.core)
    implementation(Dependencies.Libs.hilt)

    //Annotation processors
    kapt(Dependencies.AnnotationProcessors.hilt)

    //Firebase
    implementation(Dependencies.Libs.firebaseAnalytics)

    //Test libraries
    testImplementation(Dependencies.TestLibs.junit)
    androidTestImplementation(Dependencies.TestLibs.test)
    androidTestImplementation(Dependencies.TestLibs.espresso)
}

kapt {
    correctErrorTypes = true
}