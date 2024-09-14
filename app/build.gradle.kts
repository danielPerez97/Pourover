import dev.danperez.sgp.handlers.AndroidFeaturesHandler.RetainedType
import java.net.InetAddress

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("dev.danperez.scaler")
}

scaler {
    android {
        app(applicationId = "dev.danperez.scaler", namespace = "dev.danperez.scaler")
        features {
            navigation()
            compose("2023.10.01") {
                includeTestArtifact()
            }
            provideApiUrlInBuildConfig("http://${InetAddress.getLocalHost().hostAddress}:8080")
            retained(RetainedType.Activity, RetainedType.Fragment)
        }
    }
    features {
        dagger(useDaggerCompiler = true) {

        }
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

    // Dagger
    implementation(libs.dagger.api)

}