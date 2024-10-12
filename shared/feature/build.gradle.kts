plugins {
    wepli("android.library")
    wepli("android.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.wepli.shared.feature"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":domain"))
}