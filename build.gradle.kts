buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Dependencies.Plugins.androidGradle)
        classpath(Dependencies.Plugins.gms)
        classpath(Dependencies.Plugins.kotlin)
        classpath(Dependencies.Plugins.grabver)
        classpath(Dependencies.Plugins.firebaseAppDistr)
        classpath(Dependencies.Plugins.firebaseCrashlytics)
        classpath(Dependencies.Plugins.hilt)
        classpath(Dependencies.Plugins.navigation)
        classpath(Dependencies.Plugins.kotlinSerialization)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}