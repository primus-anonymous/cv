// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion by extra("1.4.20")

    repositories {
        google()
        jcenter()
        maven {
            url = java.net.URI("https://maven.fabric.io/public")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0-beta01")
        classpath("com.google.gms:google-services:4.3.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("io.fabric.tools:gradle:1.31.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        repositories {
            maven { url = java.net.URI("https://jitpack.io") }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
