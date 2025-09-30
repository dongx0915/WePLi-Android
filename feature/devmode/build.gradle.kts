plugins {
    wepli("android.feature")
    wepli("android.compose")
    wepli("android.hilt")
}

android {
    namespace = "com.wepli.feature.devmode"
}

dependencies {
    implementation(libs.json.tree)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.fcm)
}