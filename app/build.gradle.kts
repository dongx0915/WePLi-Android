plugins {
    wepli("android.application")
    wepli("android.hilt")
    wepli("android.compose")
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.wepli.app"

    packaging {
        resources {
            pickFirsts += "META-INF/INDEX.LIST"
        }
    }
}

// App 모듈에선 모든 모듈 참조 필요
dependencies {
    implementation(projects.core.common)
    implementation(projects.core.kotlin)
    implementation(projects.core.navigator)
    implementation(projects.core.resources)
    implementation(projects.designsystem)
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.shared.feature)
    implementation(projects.feature.home)
    implementation(projects.feature.search)
    implementation(projects.feature.community)
    implementation(projects.feature.playlist)
    implementation(projects.feature.relaylist)
    implementation(projects.feature.mypage)
    implementation(projects.feature.devmode)
    implementation(projects.feature.photocard)
    implementation(projects.feature.song)
    implementation(project(":devmode-fcm"))

    // Orbit
    implementation(libs.bundles.orbit)

    // Blur 라이브러리
    implementation(libs.blur.haze)
    implementation(libs.blur.haze.materials)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.fcm)

    // Supabase
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services)
    implementation(libs.android.identity.googleid)

    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest.kt)
    implementation(libs.supabase.realtime.kt)
    implementation(libs.supabase.storage.kt)

    implementation(libs.facebook.android.sdk)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.androidx.junit.ktx)

    // debugging
    implementation(libs.screen.name.viewer)
}