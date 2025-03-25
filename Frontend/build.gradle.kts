import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.SuspendInterop
import co.touchlab.skie.plugin.configuration.SkieExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.jb)
    id("dev.danperez.convention.skie") apply false
}

kotlin {
    // Define targets for different platforms
    val frameworkName = "FourSixFrontendLib"
    val xcf = XCFramework(frameworkName)

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
                export("app.cash.molecule:molecule-runtime-iosarm64:2.0.0")
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
                api(compose.material)
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
        val iosArm64Main by getting {
            dependsOn(commonMain)
            dependencies {
                api("app.cash.molecule:molecule-runtime-iosarm64:2.0.0")
            }
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(commonMain)
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
}

tasks.named("assemble") {
    dependsOn("jvmTest")
}