import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    kotlin("android")
    id("io.fabric")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}


val kotlinVersion = "1.6.10"

android {
    compileSdk = 31

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")

    implementation("com.google.firebase:firebase-database:20.0.3")
    implementation("com.google.firebase:firebase-core:20.0.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-rc01")

    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    api("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

}

kapt {
    correctErrorTypes = true
}
