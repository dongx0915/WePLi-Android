plugins {
    wepli("android.library")
    wepli("android.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.wepli.feature-shared"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":domain"))
}