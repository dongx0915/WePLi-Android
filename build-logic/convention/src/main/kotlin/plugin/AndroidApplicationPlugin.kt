package plugin

import extensions.configureAndroidCommonPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

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
class AndroidApplicationPlugin: Plugin<Project> {

    // 플러그인이 프로젝트에 적용될 때 호출
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                configureAndroidCommonPlugin()
            }
        }
    }
}