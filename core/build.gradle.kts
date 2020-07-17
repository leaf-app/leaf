plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion = Dependencies.Versions.buildTools
    defaultConfig {
        minSdkVersion(Dependencies.Versions.minSdk)
        targetSdkVersion(Dependencies.Versions.compileSdk)

        buildConfigField("String", "VK_API_VERSION", "5.120")
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
    api(Dependencies.Libs.hilt)
    api(Dependencies.Libs.hiltAndroid)
    api(Dependencies.Libs.kotlinCoroutines)
    api(Dependencies.Libs.moshi)
    api(Dependencies.Libs.navigation)
    api(Dependencies.Libs.navigationUi)
    api(Dependencies.Libs.preferences)
    api(Dependencies.Libs.retrofit)
    implementation(Dependencies.Libs.okhttp)
    implementation(Dependencies.Libs.retrofitMoshi)

    //Annotation processors
    kapt(Dependencies.AnnotationProcessors.hilt)
    kapt(Dependencies.AnnotationProcessors.hiltAndroid)
    kapt(Dependencies.AnnotationProcessors.moshi)
}