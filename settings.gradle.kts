pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "scaler-gradle-plugin"
            url = uri("https://maven.pkg.github.com/danielPerez97/Scaler")
            credentials {
                val keystoreFile = file("keystore.properties") // Do not check this file into version control since it will contain sensitive information
                val keystoreProperties = java.util.Properties()
                keystoreProperties.load(java.io.FileInputStream(keystoreFile))
                username = keystoreProperties.getProperty("githubUser") ?: error("No username")
                password = keystoreProperties.getProperty("githubToken") ?: error("No token")
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "foursix"
include(":app")
include(":foursixcore")

val localSettings = file("local.settings.gradle.kts")
if (localSettings.exists()) {
    apply(from = localSettings)
}