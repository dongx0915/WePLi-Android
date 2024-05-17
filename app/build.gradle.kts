import java.util.Properties

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

plugins {
    wepli("android.application")
    alias(libs.plugins.kotlin.android) // TODO feature 모듈 추가 후 제거 필요
}

android {
    namespace = "com.example.wepli"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wepli"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false // 코드 난독화 여부
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    signingConfigs {
        // debug는 AGP가 기본적으로 제공하므로 getByName 사용
        // debug 서명은 자동으로 생성됨
        getByName("debug") {

        }

        // release는 직접 정의해야한느 구성이므로 create 사용
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

dependencies {
    // TODO feature 모듈 추가 후 제거 필요
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}