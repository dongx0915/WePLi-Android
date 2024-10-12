plugins {
    wepli("android.library")
    wepli("android.hilt")
    wepli("kotlin.serialization")
}

android {
    namespace = "com.wepli.domain"
}

dependencies {
    implementation(project(":core:kotlin"))
}