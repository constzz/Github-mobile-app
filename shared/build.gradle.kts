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
                implementation("dev.icerock.moko:mvvm-flow-compose:0.13.1") // api mvvm-flow, binding extensions for Jetpack Compose (jvm, js, android)
                implementation("dev.icerock.moko:mvvm-livedata-compose:0.13.1") // api mvvm-livedata, binding extensions for Jetpack Compose (jvm, js, android)
                implementation("dev.icerock.moko:mvvm-livedata-material:0.13.1") // api mvvm-livedata, Material library android extensions
                implementation("dev.icerock.moko:mvvm-livedata-glide:0.13.1") // api mvvm-livedata, Glide library android extensions
                implementation("dev.icerock.moko:mvvm-livedata-swiperefresh:0.13.1") // api mvvm-livedata, SwipeRefreshLayout library android extensions
                implementation("dev.icerock.moko:mvvm-databinding:0.13.1") // api mvvm-livedata, DataBinding support for Android
                implementation("dev.icerock.moko:mvvm-viewbinding:0.13.1") // api mvvm-livedata, ViewBinding support for Android

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
            }
            kotlin {
                // export correct artifact to use all classes of library directly from Swift
                targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
                    binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
                        export("dev.icerock.moko:mvvm-core:0.13.1")
                        export("dev.icerock.moko:mvvm-livedata:0.13.1")
                        export("dev.icerock.moko:mvvm-livedata-resources:0.13.1")
                        export("dev.icerock.moko:mvvm-state:0.13.1")
                    }
                }
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