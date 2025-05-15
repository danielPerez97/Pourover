import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SuspendInterop
import co.touchlab.skie.plugin.configuration.SkieExtension
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.jb)
    id("dev.danperez.convention.skie") apply true
    alias(libs.plugins.android.library)
}

android {
    namespace = "dev.danperez.foursix.frontend"
    compileSdk = libs.versions.scaler.compilersdkVersion.get().toInt()
}

kotlin {
    // Define targets for different platforms
    val frameworkName = "FourSixFrontendLib"
    val xcf = XCFramework(frameworkName)

    androidTarget()
    jvm()
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries {
            framework {
                baseName = "FourSixFrontendLib"
                export(project(":foursixcore"))
                export(project(":Presenter"))
//                export("app.cash.molecule:molecule-runtime-iosarm64:2.0.0")
                isStatic = true
                xcf.add(this)
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.ui)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.components.resources)
                api(project(":Presenter"))
                implementation(kotlin("stdlib-common"))
                api(libs.kotlinx.coroutines)
                api(libs.molecule)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.turbine)
            }
        }
        val darwinMain by creating {
            dependsOn(commonMain)
        }
        val iosArm64Main by getting {
            dependsOn(darwinMain)
            dependencies {
//                api("app.cash.molecule:molecule-runtime-iosarm64:2.0.0")
            }
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(darwinMain)
        }
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