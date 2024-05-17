import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/**
 * 아래와 같이 ConventionPlugin이 선언 되어 있을 때
 * ```gradle
 * register("android-application") {
 *      id = "wepli.plugin.android.application" // 컨벤션 플러그인의 이름 (직접 지정)
 *      implementationClass = "plugin.AndroidApplicationPlugin" // id에 매칭되는 클래스명
 * }
 * ```
 *
 * 아래와 같이 plugin을 간단하게 적용할 수 있음
 * ```
 * @Suppress("DSL_SCOPE_VIOLATION") plugins {
 *     wepli("android.application")
 * }
 * ```
 */
fun PluginDependenciesSpec.wepli(pluginName: String) : PluginDependencySpec {
    return id("wepli.plugin.$pluginName")
}