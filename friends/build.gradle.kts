plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}

android {
    compileSdkVersion(Dependencies.Versions.compileSdk)
    buildToolsVersion = Dependencies.Versions.buildTools
    defaultConfig {
        minSdkVersion(Dependencies.Versions.minSdk)
        targetSdkVersion(Dependencies.Versions.compileSdk)
        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
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
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    //Projects
    implementation(project(":ui"))

    //Libraries
    implementation(Dependencies.Libs.hilt)
    implementation(Dependencies.Libs.hiltAndroid)
    api(Dependencies.Libs.paging)
    api(Dependencies.Libs.room)
    implementation(Dependencies.Libs.roomKtx)

    //Annotation processors
    kapt(Dependencies.AnnotationProcessors.hilt)
    kapt(Dependencies.AnnotationProcessors.hiltAndroid)
    kapt(Dependencies.AnnotationProcessors.moshi)
    kapt(Dependencies.AnnotationProcessors.room)
}