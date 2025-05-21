import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.jb)
    alias(libs.plugins.metro)
    alias(libs.plugins.wire)
    alias(libs.plugins.android.library)
    id("app.cash.sqldelight") version "2.1.0"

}

android {
    namespace = "dev.danperez.pourover.usersettings"
    compileSdk = libs.versions.scaler.compilersdkVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.scaler.minsdkVersion.get().toInt()
    }
}


sqldelight {
    databases {
        create("PouroverSettings") {
            dialect("app.cash.sqldelight:sqlite-3-24-dialect:2.1.0")
            packageName.set("dev.danperez.pourover.usersettings.sqlite")
        }
    }
}

kotlin {
    // Define targets for different platforms
    jvm()
    androidTarget()
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries {
            framework {
                baseName = "UserSettingsLib"
//                export(project(":foursixcore"))
                export(libs.molecule)
                isStatic = true
                linkerOpts.add("-lsqlite3")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
//                api(project(":foursixcore"))
                implementation(kotlin("stdlib-common"))
                api(libs.kotlinx.coroutines)
                api(libs.molecule)
                implementation(libs.okio)
                implementation(libs.wire.runtime)
                implementation("androidx.datastore:datastore-core-okio:1.1.6")
                implementation(project(":Scopes"))
                implementation(libs.store)
                implementation("app.cash.sqldelight:coroutines-extensions:2.1.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.turbine)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:android-driver:2.1.0")
            }
        }
        val darwinMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.1.0")
            }
        }
        iosArm64Main.get().dependsOn(darwinMain)
        iosSimulatorArm64Main.get().dependsOn(darwinMain)
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                // Add JVM-specific dependencies here
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }
    }
}

wire {
    kotlin {}
    sourcePath {
        srcDir("src/commonMain/proto")
    }
}

tasks.withType<KotlinJvmCompile>() {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.FAILED)
    }

}

tasks.named("assemble") {
    dependsOn("jvmTest")
}