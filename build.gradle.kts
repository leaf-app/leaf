buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.Plugins.androidGradle)
        classpath(Dependencies.Plugins.gms)
        classpath(Dependencies.Plugins.kotlin)
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