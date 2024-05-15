import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class KotlinSerializationPlugin : Plugin<Project> {

    companion object {
        const val IMPLEMENTATION = "implementation"
    }

    override fun apply(target: Project) = with(target) {
        with(plugins) {
            apply("org.jetbrains.kotlin.plugin.serialization")
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            IMPLEMENTATION(libs.findLibrary("kotlin.serialization.json").get())
        }
    }
}
