plugins {
    wepli("android.library")
    wepli("android.compose")
}

android {
    namespace = "com.wepli.designsystem"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:resources"))
    implementation(project(":shared:feature"))

    implementation(libs.coil)
    implementation(libs.blur.haze)
    implementation(libs.blur.haze.materials)
    implementation(libs.youtube.player)
    implementation(libs.androidx.material3.window.size.clazz)
}
