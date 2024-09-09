package plugin

import com.android.build.api.dsl.ApplicationExtension
import extensions.configureKotlinAndroid
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.konan.properties.loadProperties
import java.io.File

/**
 * Android 프로젝트에서 반복되는 설정을 플러그인으로 구조화
 * 멀티 모듈을 사용하면 build.gradle에 중복 코드가 많아지는데, ConventionPlugin을 통해 쉽게 관리 가능
 *
 * 아래와 같이 선언하고 필요한 모듈에서 id로 추가해주면 됨
 *
 * ```gradle
 * gradlePlugin {
 *      plugins {
 *          register("com.example.wepli.plugin.AndroidApplicationPlugin") {
 *              id = "wepli.plugin.application" // 컨벤션 플러그인의 이름 (직접 지정)
 *              implementationClass = "AndroidApplicationConventionPlugin" // id에 매칭되는 클래스명 (구현체)
 *          }
 *      }
 * }
 * ```
 *
 * 기존 gradle 문법
 * ```
 * apply plugin: 'org.jetbrains.kotlin.android'
 * ```
 * ->
 * Convention Plugin 문법
 * ```
 * apply("org.jetbrains.kotlin.android")
 * ```
 */
// App 모듈에 설정될 Gradle
class AndroidApplicationPlugin: Plugin<Project> {

    // 플러그인이 프로젝트에 적용될 때 호출
    override fun apply(target: Project) {
        val file = File(target.rootProject.rootDir, "local.properties")
        val localProperties = loadProperties(file.absolutePath)

        val appVersion = target.libs.findVersion("appVersion").get().requiredVersion
        val appVersionCode = target.libs.findVersion("versionCode").get().requiredVersion.toInt()
        val targetSdkVersion = target.libs.findVersion("targetSdk").get().requiredVersion.toInt()

        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    applicationId = "com.wepli.wepli"
                    versionName = appVersion
                    versionCode = appVersionCode

                    targetSdk = targetSdkVersion
                }

                signingConfigs {
                    // debug는 AGP가 기본적으로 제공하므로 getByName 사용
                    // debug 서명은 자동으로 생성됨
                    getByName("debug") {

                    }

                    // release는 직접 정의해야하는 구성이므로 create 사용
                    create("release") {

                    }
                }

                buildTypes {
                    debug {
                        isDebuggable = true
                        signingConfig = signingConfigs.getByName("debug")
                    }

                    release {
                        isMinifyEnabled = true // 코드 난독화 여부
                        isShrinkResources = true
                        signingConfig = signingConfigs.getByName("release")
                    }
                }
            }
        }
    }
}