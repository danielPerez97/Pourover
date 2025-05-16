pluginManagement {
    includeBuild("build-logic")
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
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        if (System.getenv("DISABLE_SCALER") != "true") {
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

if (System.getenv("DISABLE_APP") != "true") {
    include(":app")
}

if (System.getenv("DISABLE_FOURSIXCORE") != "true") {
    include(":foursixcore")
}

if (System.getenv("DISABLE_FRONTEND") != "true") {
    include(":Frontend")
}

if (System.getenv("DISABLE_PRESENTER") != "true") {
    include(":Presenter")
}

if(System.getenv("DISABLE_USERSETTINGS") != "true") {
    include(":UserSettings")
}

val localSettings = file("local.settings.gradle.kts")
if (localSettings.exists()) {
    apply(from = localSettings)
}