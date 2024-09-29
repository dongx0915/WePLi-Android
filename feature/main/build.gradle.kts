plugins {
    wepli("android.feature")
    wepli("android.compose")
    wepli("android.hilt")
}

android {
    namespace = "com.wepli.wepli.feature.main"
}

dependencies {
    implementation(libs.material)
}