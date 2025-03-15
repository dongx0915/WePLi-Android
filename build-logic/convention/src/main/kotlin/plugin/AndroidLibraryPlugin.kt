package plugin

import com.android.build.gradle.LibraryExtension
import extensions.configureKotlinAndroid
import extensions.implementation
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                defaultConfig.consumerProguardFiles("consumer-rules.pro")
                packaging {
                    resources {
                        excludes += "/META-INF/LICENSE*"
                    }
                }
            }

            dependencies {
                implementation(libs.findLibrary("joda.time").get())
            }
        }
    }
}