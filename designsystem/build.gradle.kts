plugins {
    wepli("android.library")
    wepli("android.compose")
}

android {
    namespace = "com.wepli.designsystem"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":shared:feature"))

    implementation(libs.coil)
    implementation(libs.blur.haze)
    implementation(libs.blur.haze.materials)
}
