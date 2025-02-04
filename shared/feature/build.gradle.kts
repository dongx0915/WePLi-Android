plugins {
    wepli("android.library")
    wepli("android.hilt")
    wepli("android.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.wepli.shared.feature"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":domain"))
}