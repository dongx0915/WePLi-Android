plugins {
    wepli("android.application")
    wepli("android.hilt")
    wepli("android.compose")
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.wepli.app"
}

// App 모듈에선 모든 모듈 참조 필요
dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.search)
    implementation(projects.feature.community)
    implementation(projects.feature.playlist)
    implementation(projects.feature.mypage)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.core.common)
    implementation(projects.core.kotlin)
    implementation(projects.core.navigator)
    implementation(projects.designsystem)
    implementation(projects.shared.feature)

    // Orbit
    implementation(libs.bundles.orbit)

    // Blur 라이브러리
    implementation(libs.blur.haze)
    implementation(libs.blur.haze.materials)

    // Firebase
    implementation(platform(libs.firebase.bom))

    // Supabase
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services)
    implementation(libs.android.identity.googleid)

    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest.kt)
    implementation(libs.supabase.realtime.kt)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.androidx.junit.ktx)
}