plugins {
    wepli("android.library")
    wepli("android.compose")
}

android {
    namespace = "com.wepli.core.common"
}

dependencies {
    implementation(libs.gson)

    // Orbit
    implementation(libs.bundles.orbit)
}