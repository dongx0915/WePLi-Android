import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltPlugin : Plugin<Project> {

    companion object {
        const val IMPLEMENTATION = "implementation"
        const val KAPT = "kapt"
    }

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                IMPLEMENTATION(libs.findLibrary("hilt-android").get())
                KAPT(libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}