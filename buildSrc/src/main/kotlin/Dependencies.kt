object Dependencies {

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradle}"
        const val gms = "com.google.gms:google-services:${Versions.gms}"
        const val grabver = "eu.davidea:grabver:${Versions.grabver}"
        const val firebaseAppDistr =
            "com.google.firebase:firebase-appdistribution-gradle:${Versions.firebaseAppDistr}"
        const val firebaseCrashlytics =
            "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsPlugin}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
        const val navigation =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        const val kotlinSerialization =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
    }

    object Libs {
        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val coil = "io.coil-kt:coil:${Versions.coil}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val core = "androidx.core:core-ktx:${Versions.core}"
        const val firebaseAnalytics =
            "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}"
        const val firebaseConfig =
            "com.google.firebase:firebase-config-ktx:${Versions.firebaseConfig}"
        const val firebaseCrashlytics =
            "com.google.firebase:firebase-crashlytics:${Versions.firebaseCrashlytics}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltAndroid = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltAndroid}"
        const val kotlinCoroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
        const val kotlinSerialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerialization}"
        const val ktor = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val ktorLogging = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
        const val ktorOkHttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        const val ktorSerialization = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
        const val lifecycleCommon =
            "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
        const val lifecycleLiveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val lifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val materialComponents =
            "com.google.android.material:material:${Versions.materialComponents}"
        const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val webkit = "androidx.webkit:webkit:${Versions.webkit}"
    }

    object AnnotationProcessors {
        const val hilt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val hiltAndroid = "androidx.hilt:hilt-compiler:${Versions.hiltAndroid}"
    }

    object TestLibs {
        const val test = "androidx.test:core:${Versions.test}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val junit = "junit:junit:${Versions.junit}"
        const val navigation = "androidx.navigation:navigation-testing:${Versions.navigation}"
    }

    //A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    object Versions {
        const val activity = "1.2.0-alpha06"
        const val androidGradle = "4.2.0-alpha03"
        const val test = "1.3.0-rc01"
        const val appcompat = "1.3.0-alpha01"
        const val buildTools = "30.0.0"
        const val coil = "0.11.0"
        const val compileSdk = 30
        const val constraintLayout = "2.0.0-beta7"
        const val core = "1.4.0-alpha01"
        const val espresso = "3.2.0"
        const val firebaseAnalytics = "17.4.3"
        const val firebaseAppDistr = "2.0.0"
        const val firebaseConfig = "19.1.4"
        const val firebaseCrashlytics = "17.1.0"
        const val firebaseCrashlyticsPlugin = "2.2.0"
        const val fragment = "1.3.0-alpha06"
        const val gms = "4.3.3"
        const val grabver = "2.0.2"
        const val hilt = "2.28.1-alpha"
        const val hiltAndroid = "1.0.0-alpha01"
        const val junit = "4.13"
        const val jvm = "1.8"
        const val kotlin = "1.3.72"
        const val kotlinCoroutines = "1.3.7"
        const val kotlinSerialization = "0.20.0"
        const val ktor = "1.3.2"
        const val lifecycle = "2.3.0-alpha05"
        const val materialComponents = "1.3.0-alpha01"
        const val minSdk = 23
        const val navigation = "2.3.0"
        const val webkit = "1.3.0-rc01"
    }

}