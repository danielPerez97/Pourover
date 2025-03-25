import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import co.touchlab.skie.plugin.configuration.SkieExtension
import co.touchlab.skie.configuration.SuspendInterop
import co.touchlab.skie.configuration.FlowInterop

class SkieConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("co.touchlab.skie")
            }

            extensions.configure<SkieExtension> {
                configureSkie(this)
            }
        }
    }

    private fun Project.configureSkie(extension: SkieExtension) {
        extension.features {
            // Use .set() for most properties
            enableSwiftUIObservingPreview.set(true)
            coroutinesInterop.set(true)

            // Use the group configuration method
            group {
                // Note: The actual method names might need to match the exact implementation
                SuspendInterop.Enabled(true)
                FlowInterop.Enabled(true) // or false
            }
        }
    }
}