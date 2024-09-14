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
