plugins {
    wepli("android.feature")
    wepli("android.compose")
    wepli("android.hilt")
}

android {
    namespace = "com.wepli.feature.home"
}
dependencies {
    implementation(libs.androidx.material3.window.size.clazz)
}