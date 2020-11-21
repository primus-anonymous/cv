plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("io.fabric")
}


val kotlinVersion = "1.4.10"

android {
    compileSdkVersion(29)

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")

    implementation("com.google.firebase:firebase-database:19.5.0")
    implementation("com.google.firebase:firebase-core:17.5.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

    val koinVersion = "2.0.1"

    api("org.koin:koin-core:$koinVersion")
    api("org.koin:koin-androidx-viewmodel:$koinVersion")
}

