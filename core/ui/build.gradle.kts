plugins {
    wepli("android.library")
    wepli("android.compose")
}

android {
    namespace = "com.wepli.wepli.core.ui"
}

dependencies {
    implementation(project(":core:common"))
}
