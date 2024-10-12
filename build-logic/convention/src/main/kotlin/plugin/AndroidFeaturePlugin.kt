package plugin

import com.android.build.gradle.LibraryExtension
import extensions.configureKotlinAndroid
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Feature 모듈에서 공통으로 사용될 라이브러리들을 선언
 */
class AndroidFeaturePlugin : Plugin<Project> {

    companion object {
        private const val IMPLEMENTATION = "implementation"
    }

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        val extension = extensions.getByType<LibraryExtension>()
        configureKotlinAndroid(extension)

        extensions.configure<LibraryExtension> {
            defaultConfig.consumerProguardFiles("consumer-rules.pro")
        }

        dependencies {
            // TODO Feature 모듈에서 공통으로 참조하는 모듈을 선언
            IMPLEMENTATION(project(":core:common"))
            IMPLEMENTATION(project(":core:kotlin"))
            IMPLEMENTATION(project(":core:navigator"))
            IMPLEMENTATION(project(":shared:feature"))
            IMPLEMENTATION(project(":designsystem"))
            IMPLEMENTATION(project(":domain"))

            IMPLEMENTATION(libs.findLibrary("coil").get())
            IMPLEMENTATION(libs.findLibrary("material").get())
        }
    }
}