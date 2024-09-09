import java.util.Properties

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

plugins {
    wepli("android.application")
}

android {
    namespace = "com.example.wepli"

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

dependencies {
    // Featrue
    implementation(projects.feature.main)
}