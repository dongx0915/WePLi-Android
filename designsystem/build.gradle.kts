plugins {
    wepli("android.library")
    wepli("android.compose")
}

android {
    namespace = "com.wepli.wepli.designsystem"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":domain"))
    implementation(libs.coil)
}
