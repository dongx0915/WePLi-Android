plugins {
    wepli("android.library")
    wepli("android.hilt")
}

android {
    namespace = "com.wepli.devmode.fcm"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core:common"))

    // Google Auth for Firebase Admin SDK
    implementation(libs.google.credential) {
        exclude(group = "com.google.api", module = "api-common")
        exclude(group = "org.apache.httpcomponents")
    }
}