plugins {
    wepli("android.library")
    wepli("android.hilt")
}

android {
    namespace = "com.wepli.core.navigator"
}

dependencies {
    implementation(project(":core:common"))
}