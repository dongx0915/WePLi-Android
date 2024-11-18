import java.util.Properties

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

plugins {
    wepli("android.application")
    wepli("android.hilt")
    wepli("android.compose")
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.wepli.app"

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

// App 모듈에선 모든 모듈 참조 필요
dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.community)
    implementation(projects.feature.playlist)
    implementation(projects.feature.mypage)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.core.common)
    implementation(projects.core.navigator)
    implementation(projects.designsystem)
    implementation(projects.shared.feature)

    // Blur 라이브러리
    implementation(libs.blur.haze)
    implementation(libs.blur.haze.materials)

    // Firebase
    implementation(platform(libs.firebase.bom))
}