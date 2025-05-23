import dev.danperez.sgp.handlers.AndroidFeaturesHandler.RetainedType
import java.net.InetAddress

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.metro)
    id("dev.danperez.scaler") version "0.24.0"
}

scaler {
    android {
        app(applicationId = "dev.danperez.foursix", namespace = "dev.danperez.foursix")
        features {
            navigation()
            compose("2025.05.00") {
                activity()
                includeTestArtifact()
            }
            retained(RetainedType.Activity, RetainedType.Fragment)
        }
    }
    features {
//        dagger(useDaggerCompiler = true) {
//            disableAnvil()
//        }
        okHttp()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":foursixcore"))
    implementation(project(":Frontend"))
    implementation(project(":Presenter"))
    implementation(project(":UserSettings"))
    implementation(project(":Scopes"))

    // Dagger

}