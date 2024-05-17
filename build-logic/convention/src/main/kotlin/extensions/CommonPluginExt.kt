package extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import plugin.AndroidHiltPlugin
import plugin.AndroidKotlinPlugin
import plugin.KotlinSerializationPlugin
import java.util.Properties

/**
 * 공통 Kotlin Android 기본 설정
 * Application, Feature에 적용
 */
internal fun Project.configureAndroidCommonPlugin() {
    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    with(plugins) {
        apply<AndroidKotlinPlugin>()
        apply<KotlinSerializationPlugin>()
        apply("kotlin-parcelize")
        // apply<AndroidHiltPlugin>()
    }

    extensions.getByType<BaseExtension>().apply {
        defaultConfig {
            // TODO
            // BuildConfig, baseUrl, apiKey 등 local.properties에 작성하던 것들을 작성
        }

        buildFeatures.apply {
            viewBinding = true
            buildConfig = true
        }
    }

    dependencies {
        // TODO feature 모듈에서 공통으로 사용되는 의존성 추가
        // core.ktx, appcompat, lifecycle, material, fragment.ktx, etc...
    }
}