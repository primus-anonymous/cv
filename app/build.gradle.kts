import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("com.google.gms.google-services")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.neocaptainnemo.cv"
        minSdk = 21
        targetSdk = 31
        versionCode = 11
        versionName = "1.9.1"
        multiDexEnabled = true

        // testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-rc02"
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {

    val kotlinVersion: String by rootProject.extra
    val hiltAndroid: String by rootProject.extra

    implementation(project(":core"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.7.0")

    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.multidex:multidex:2.0.1")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")

    implementation("io.coil-kt:coil:1.1.1")

    implementation("com.google.dagger:hilt-android:$hiltAndroid")
    kapt("com.google.dagger:hilt-android-compiler:$hiltAndroid")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-rc01")

    api("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    val composeVersion = "1.0.5"

    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc02")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-rc01")

    val kotest = "5.0.3"

    testImplementation("io.kotest:kotest-assertions-core:$kotest")
    testImplementation("io.kotest:kotest-runner-junit5:$kotest")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation("app.cash.turbine:turbine:0.7.0")
    testImplementation("io.mockk:mockk:1.12.2")

//    androidTestImplementation("androidx.ui:ui-test:$composeToolingVersion")
}

kapt {
    correctErrorTypes = true
}
