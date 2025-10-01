plugins {
    wepli("android.feature")
    wepli("android.hilt")
    wepli("android.compose")
    id("kotlinx-serialization")
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

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    // Kotlin Serialization
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.converter)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.fcm)
}