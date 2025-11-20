plugins {
    wepli("android.feature")
    wepli("android.hilt")
    wepli("android.compose")
    id("kotlinx-serialization")
}

android {
    namespace = "com.wepli.devmode.network"
}

dependencies {
    implementation(libs.json.tree)
}