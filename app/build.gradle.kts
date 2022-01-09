import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

val kotlinVersion = "1.6.10"

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
}

dependencies {

    implementation(project(":core"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.7.0")

    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.multidex:multidex:2.0.1")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")

    testImplementation("junit:junit:4.13.2")

    implementation("io.coil-kt:coil:1.1.1")

    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

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

    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")

//    androidTestImplementation("androidx.ui:ui-test:$composeToolingVersion")
}

apply(plugin = "com.google.gms.google-services")

kapt {
    correctErrorTypes = true
}
