plugins {
    wepli("android.feature")
    wepli("android.compose")
    wepli("android.hilt")
}

android {
    namespace = "com.wepli.feature.song"
}

dependencies {
    implementation(libs.androidx.material3.window.size.clazz)
    implementation(libs.youtube.player)
}