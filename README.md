# KMP-ROOM Database

A Kotlin Multiplatform project with RoomDB integration, enabling database operations across Android and iOS (Kotlin & Swift).

## **✨ Features**

- Cross-platform database persistence using RoomDB.
- Shared business logic in Kotlin.
- iOS support via Swift interoperability.

## **Architecture**

- Utilizes Kotlin Multiplatform for shared logic.
- Android and iOS modules handle platform-specific UI.
- RoomDB for local data storage.

## **🗂️ Project Structure**

- `.fleet/` - CI/CD or automation configuration.
- `composeApp/` - Likely contains UI/app logic for Compose.
- `iosApp/` - iOS-specific code.
- `shared/` - Shared Kotlin codebase (business logic, data models).
- Build/config files: `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`, `.gitignore`, etc.
- Gradle wrapper and scripts.

## **🚀 Getting Started**

Clone the repo:
```bash
git clone https://github.com/ramsaran09/Kmp-RoomDB.git
```
Open in Android Studio (for Android) or Xcode (for iOS).

To build/run Android:
- Open `composeApp` module and run.

To build/run iOS:
- Open `iosApp` in Xcode and run on simulator/device.

Run with Gradle:
```bash
./gradlew build
```

## **🧩 Dependencies**

- Kotlin Multiplatform
- RoomDB
- Gradle
