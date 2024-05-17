package plugin

import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 공식 문서 상의 hilt 적용 가이드
 *
 * 해당 gradle을 ConventionPlugin으로 변경
 * ```gradle
 * // app/build.gradle
 * apply plugin: 'kotlin-kapt'
 * apply plugin: 'dagger.hilt.android.plugin'
 *
 * android {
 *     ...
 * }
 *
 * dependencies {
 *     ...
 *     implementation "com.google.dagger:hilt-android:$hilt_version"
 *     kapt "com.google.dagger:hilt-android-compiler:$hilt_version"
 * }
 * ```
 */
class AndroidHiltPlugin : Plugin<Project> {

    companion object {
        const val IMPLEMENTATION = "implementation"
        const val KAPT = "kapt"
    }

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }

            dependencies {
                IMPLEMENTATION(libs.findLibrary("hilt-android").get())
                KAPT(libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}