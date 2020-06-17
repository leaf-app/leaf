object Dependencies {

    object Plugins {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradle}"
        const val gms = "com.google.gms:google-services:${Versions.gms}"
    }

    object Libs {
        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val core = "androidx.core:core-ktx:${Versions.core}"
        const val firebaseAnalytics =
            "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalytics}"
        const val materialComponents =
            "com.google.android.material:material:${Versions.materialComponents}"
    }

    object TestLibs {
        const val test = "androidx.test:core:${Versions.test}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val junit = "junit:junit:${Versions.junit}"
    }

    //A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
    object Versions {
        const val androidGradle = "4.2.0-alpha01"
        const val test = "1.3.0-rc01"
        const val appcompat = "1.3.0-alpha01"
        const val core = "1.4.0-alpha01"
        const val espresso = "3.2.0"
        const val firebaseAnalytics = "17.4.3"
        const val gms = "4.3.3"
        const val junit = "4.13"
        const val kotlin = "1.3.72"
        const val materialComponents = "1.3.0-alpha01"
    }

}