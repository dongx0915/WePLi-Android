// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        // 플러그인을 사용할 수 있도록 클래스 패스에 추가
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.android.gradle.plugin)
        classpath(libs.hilt.gradle.plugin)
    }
}

plugins {
    /**
     * 플러그인 정의 (적용은 x)
     * Version Catalog에 작성한 plugins 들을 모두 정의
     * 필요할 때 특정 모듈에서만 적용할 수 있음
     */
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.dagger.hilt) apply false
}