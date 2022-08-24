# Github mobile app

It is a cross-platfrom mobile application. If fetches data from GithubAPI and caches it.

It is built following **Clean Code** principles for Shared module writen in Kotlin.
Shared module holds all the bussiness logic.
Native modules are observing the state of Shared module using shared ViewModels and observing LiveData.

UI is built using:
  - SwiftUI (iOS)
  - Compose (Android)

KMM technologies used in project: 
- Kodein for DI
- Ktor for Networking
- Moko MVVM for convenience in creating shared view models and managing their lifecycle

