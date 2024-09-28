plugins {
    wepli("android.feature")
    wepli("android.compose")
}

android {
    namespace = "com.wepli.wepli.feature.main"
}

dependencies {
    implementation(libs.material)
}