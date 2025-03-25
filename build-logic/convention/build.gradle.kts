import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "dev.danperez.convention.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(versionCatalog.agp)
    compileOnly(versionCatalog.skie)
}

gradlePlugin {
    // register the convention plugin
    plugins {
        register("skieConfiguration") {
            id = "dev.danperez.convention.skie"
            implementationClass = "SkieConventionPlugin"
        }
    }
}