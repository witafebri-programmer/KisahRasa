# 🍲 KisahRasa - Modern Android Recipe App

KisahRasa is a robust, secure, and fully modularized Android application designed to help users discover, search, and save their favorite culinary recipes. Built with modern Android development practices, it heavily emphasizes clean architecture, reactive programming, and top-tier security standards.

## ✨ Key Features
* **Discover & Search:** Explore a wide variety of food recipes powered by [TheMealDB API](https://www.themealdb.com/).
* **Favorite Recipes:** Save recipes locally for offline viewing.
* **User Authentication:** Secure login and profile management utilizing Firebase Auth.
* **Dynamic Feature:** The 'Favorite' module is built as a Dynamic Feature Module (DFM) to optimize the base app size.
* **Edge-to-Edge UI:** Modern and immersive user interface supporting dark and light themes.

## 🏗️ Architecture & Tech Stack
This project enforces a strict **Clean Architecture** (Data, pure Domain, and Presentation layers) and **Modularization** (App, Core, and Dynamic Feature modules).

* **Language:** Kotlin
* **Asynchronous:** Coroutines & StateFlow/Flow
* **Dependency Injection:** * Dagger Hilt (for App and Core modules)
  * Pure Dagger 2 (for Dynamic Feature Module utilizing `EntryPointAccessors`)
* **Network:** Retrofit2 & OkHttp3
* **Local Database:** Room Persistence Library
* **Image Loading:** Glide
* **Memory Management:** LeakCanary (Debug mode)

## 🛡️ Security Implementations
KisahRasa takes user data and app integrity seriously, implementing DevSecOps principles:
* **Database Encryption:** Powered by SQLCipher to encrypt local Room databases.
* **Certificate Pinning:** Hardcoded SHA-256 hashes in OkHttp to prevent Man-in-the-Middle (MitM) attacks.
* **Secure Storage:** `EncryptedSharedPreferences` for storing sensitive user sessions.
* **Root Detection:** Utilizing RootBeer to detect and handle compromised/rooted environments.
* **Code Obfuscation:** ProGuard/R8 enabled for release builds.

## 🚀 CI/CD Pipeline
Continuous Integration is automated using **GitHub Actions**. The pipeline ensures code quality and stability by automatically triggering on every push/pull request to perform:
* Unit Testing (JUnit4, Mockito, Coroutines Test)
* Android Lint & Code Style Checks
* Debug APK Assembly
* Dependency Vulnerability Analysis (via GitHub Dependabot)

## 🛠️ How to Run the Project Locally

To clone and run this project, you'll need [Git](https://git-scm.com) and [Android Studio](https://developer.android.com/studio) installed on your computer.

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/witafebri-programmer/KisahRasa.git](https://github.com/witafebri-programmer/KisahRasa.git)


2. Open in Android Studio: Open the cloned folder in Android Studio.

4. Configure Firebase (google-services.json): For security reasons, the Firebase configuration file is not included in this repository.
5. Create a new project in Firebase Console.
6. Register the Android app with the package name com.dicoding.kisahrasa (adjust if you changed it).
7. Download the google-services.json file and place it in the app/ directory.
8. Configure API Keys (local.properties):
9. Add your API keys to the local.properties file in the root directory:

Properties
# Add your NVD API Key if you plan to run OWASP Dependency-Check locally
NVD_API_KEY="your_nvd_api_key_here"
10. Build and Run: Sync the project with Gradle files and click the Run 'app' button.

👨‍💻 Author
Wita Febri

GitHub: @witafebri-programmer
