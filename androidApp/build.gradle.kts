plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation("androidx.compose.ui:ui:1.2.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.2.1")
    // Material Design
    implementation("androidx.compose.material:material:1.2.1")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.2.1")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:1.2.1")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.2.1")
    // Activity Compose
    implementation("androidx.activity:activity-compose:1.5.0-beta01")
    implementation ("androidx.compose.runtime:runtime-livedata:1.3.0-alpha03")

    implementation("dev.icerock.moko:mvvm-flow-compose:0.13.1") // api mvvm-flow, binding extensions for Jetpack Compose (jvm, js, android)
    implementation("dev.icerock.moko:mvvm-livedata-compose:0.13.1") // api mvvm-livedata, binding extensions for Jetpack Compose (jvm, js, android)
    implementation("dev.icerock.moko:mvvm-livedata-material:0.13.1") // api mvvm-livedata, Material library android extensions
    implementation("dev.icerock.moko:mvvm-livedata-glide:0.13.1") // api mvvm-livedata, Glide library android extensions
    implementation("dev.icerock.moko:mvvm-livedata-swiperefresh:0.13.1") // api mvvm-livedata, SwipeRefreshLayout library android extensions
    implementation("dev.icerock.moko:mvvm-databinding:0.13.1") // api mvvm-livedata, DataBinding support for Android
    implementation("dev.icerock.moko:mvvm-viewbinding:0.13.1") // api mvvm-livedata, ViewBinding support for Android


}