import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dynamic.feature) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.dependency.check) apply false
    id("jacoco")
}

// Function to fetch NVD API Key securely
fun getNvdApiKey(): String? {
    // 1. Try Environment Variable (CI)
    val envKey = System.getenv("NVD_API_KEY")
    if (!envKey.isNullOrEmpty()) return envKey

    // 2. Fallback to local.properties (Local)
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
        return localProperties.getProperty("nvd.api.key")
    }

    return null
}

subprojects {
    apply(plugin = "org.owasp.dependencycheck")

    configure<org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension> {
        nvd {
            apiKey = getNvdApiKey()
        }
        // Speed up analysis by disabling unused analyzers if needed
        analyzers {
            assemblyEnabled = false
        }
        format = "HTML"
    }
}
