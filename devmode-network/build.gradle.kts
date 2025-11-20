plugins {
    wepli("android.feature")
    wepli("android.hilt")
    wepli("android.compose")
    id("kotlinx-serialization")
}

android {
    namespace = "com.wepli.devmode.network"
}

dependencies {
    implementation(libs.json.tree)
    implementation(libs.okhttp3)
    implementation(libs.kotlin.serialization.json)

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler.ksp)
}