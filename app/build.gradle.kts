plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val kotlinVersion = "1.4.21"

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.neocaptainnemo.cv"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 10
        versionName = "1.9.0"
        multiDexEnabled = true

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"),
                          "proguard-rules.pro")
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = "1.4.21"
        kotlinCompilerExtensionVersion = "1.0.0-alpha10"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>()
        .configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs.toMutableList()
                        .apply {
                            add("-Xallow-jvm-ir-dependencies")
                            add("-Xskip-prerelease-check")
                        }
            }
        }

dependencies {

    implementation(project(":core"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.3.2")

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.multidex:multidex:2.0.1")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")

    testImplementation("junit:junit:4.13.1")

    implementation("io.coil-kt:coil:0.10.1")

    val composeVersion = "1.0.0-alpha10"
    val composeToolingVersion = "1.0.0-alpha07"

    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.ui:ui-tooling:$composeToolingVersion")


    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testImplementation("org.assertj:assertj-core:2.6.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")

    androidTestImplementation("androidx.ui:ui-test:$composeToolingVersion")
}

apply(plugin = "com.google.gms.google-services")
