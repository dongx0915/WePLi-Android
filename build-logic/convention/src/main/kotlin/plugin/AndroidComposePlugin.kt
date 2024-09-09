package plugin

import com.android.build.gradle.BaseExtension
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<BaseExtension> {
                buildFeatures.compose = true
            }

            dependencies {
                "implementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
                "implementation"(libs.findBundle("androidx-compose").get())
                "ksp"(libs.findLibrary("androidx-compose-destination-compiler").get())
            }
        }
    }
}