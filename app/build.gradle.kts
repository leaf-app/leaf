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
    id("com.google.firebase.crashlytics")
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
            enableV3Signing = true
            enableV4Signing = true
        }
    }
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion = Dependencies.Versions.buildTools
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
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
            addManifestPlaceholders(mapOf("crashlyticsCollectionEnabled" to "true"))
        }
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            versionNameSuffix = versioning.build.toString()
            signingConfig = signingConfigs.getByName("default")
            addManifestPlaceholders(mapOf("crashlyticsCollectionEnabled" to "false"))
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
    implementation(project(":core"))

    //Libraries
    implementation(Dependencies.Libs.hilt)
    implementation(Dependencies.Libs.hiltAndroid)

    //Annotation processors
    kapt(Dependencies.AnnotationProcessors.hilt)
    kapt(Dependencies.AnnotationProcessors.hiltAndroid)

    //Firebase
    implementation(Dependencies.Libs.firebaseAnalytics)
    implementation(Dependencies.Libs.firebaseConfig)

    //Test libraries
    testImplementation(Dependencies.TestLibs.junit)
    androidTestImplementation(Dependencies.TestLibs.test)
    androidTestImplementation(Dependencies.TestLibs.espresso)
}

kapt {
    correctErrorTypes = true
}