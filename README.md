# GithubMultiplatformTestTask

This is an ultimate test task for **Mobile Developer** role. 

It is a cross-platfrom mobile application. If fetches data from GithubAPI and caches it.

It is built following **Clean Code** principles for Shared module written in Kotlin.
Shared module holds all the bussiness logic.

All the UI is built natively using SwiftUI(for iOS) and Compose(for Android).
Native modules are observing the state of Shared module using shared ViewModels and observing LiveData.

KMM technologies used in project: 
- Kodein for DI
- Ktor for Networking
- Moko MVVM for convenience in creatin shared view models and managing their lifecycle
