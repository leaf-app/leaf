import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("eu.davidea.grabver")
    id("com.google.firebase.appdistribution")
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
    compileSdkVersion(30)
    buildToolsVersion = "30.0.0"

    defaultConfig {
        applicationId = "ru.dzgeorgy.leaf"
        minSdkVersion(23)
        targetSdkVersion(30)
        versionCode = versioning.code
        versionName = versioning.name

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
            firebaseAppDistribution {
                releaseNotesFile = rootDir.absolutePath + "/log.txt"
                groupsFile = rootDir.absolutePath + "/group.txt"
                serviceCredentialsFile =
                    rootDir.absolutePath + "/ru-dzgeorgy-leaf-efa8ee88d103.json"
            }
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            versionNameSuffix = versioning.build.toString()
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