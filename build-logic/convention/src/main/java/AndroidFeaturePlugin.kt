import com.example.convention.configureAndroidCommonPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Feature 모듈에서 공통으로 사용될 라이브러리들을 선언
 */
class AndroidFeaturePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        plugins.apply("com.android.library")
        configureAndroidCommonPlugin()
    }
}