plugins {
    id("com.android.library")
    kotlin("android")
    id("io.fabric")
    id("kotlin-parcelize")
    kotlin("kapt")
}


android {
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    namespace = "com.neocaptainnemo.cv.core"
}

dependencies {

    val kotlinVersion: String by rootProject.extra
    val hiltAndroid: String by rootProject.extra

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-core:21.1.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("com.google.dagger:hilt-android:$hiltAndroid")
    kapt("com.google.dagger:hilt-android-compiler:$hiltAndroid")
}

kapt {
    correctErrorTypes = true
}
