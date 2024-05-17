package plugin

import com.android.build.gradle.BaseExtension
import extensions.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AndroidKotlinPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val compileSdkVersion = libs.findVersion("compileSdk").get().requiredVersion.toInt()
        val targetSdkVersion = libs.findVersion("targetSdk").get().requiredVersion.toInt()
        val minSdkVersion = libs.findVersion("minSdk").get().requiredVersion.toInt()

        with(plugins) {
            apply("kotlin-android")
        }

        extensions.getByType<BaseExtension>().apply {
            setCompileSdkVersion(compileSdkVersion)

            defaultConfig {
                minSdk = minSdkVersion
                targetSdk = targetSdkVersion
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            (this as ExtensionAware).configure<KotlinJvmOptions> {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }

            dependencies {
                // TODO Kotlin 관련 의존성 추가
                // coroutines, datetime, etc...
            }
        }
    }
}