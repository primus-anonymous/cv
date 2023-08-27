// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion by extra("1.8.22")
    val hiltAndroid by extra("2.47")

    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("io.fabric.tools:gradle:1.31.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltAndroid")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        repositories {
            maven { url = uri("https://jitpack.io") }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
