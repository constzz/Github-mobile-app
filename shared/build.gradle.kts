plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.21"
}

// Memory manager for Kotlin/Native
kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
    }
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    // Dependencies constats
    val ktorVersion = "2.0.2"
    val coroutinesVersion = "1.6.4"
    val devIcerockMokoMvvmVersion = "0.13.1"
    val kodeinVersion =  "7.14.0"

    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation("org.kodein.di:kodein-di:$kodeinVersion")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                implementation("dev.icerock.moko:mvvm-core:0.13.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
                implementation("dev.icerock.moko:mvvm-flow:0.13.1") // api mvvm-core, CFlow for native and binding extensions
                implementation("dev.icerock.moko:mvvm-livedata:0.13.1") // api mvvm-core, LiveData and extensions
                implementation("dev.icerock.moko:mvvm-state:0.13.1") // api mvvm-livedata, ResourceState class and extensions
                implementation("dev.icerock.moko:mvvm-livedata-resources:0.13.1") // api mvvm-core, moko-resources, extensions for LiveData with moko-resources
                implementation("dev.icerock.moko:kswift-runtime:0.6.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("dev.icerock.moko:mvvm-core:0.13.1")
                implementation("dev.icerock.moko:mvvm-livedata:0.13.1")
                implementation("dev.icerock.moko:mvvm-livedata-resources:0.13.1")
                implementation("dev.icerock.moko:mvvm-state:0.13.1")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 32
    }
}