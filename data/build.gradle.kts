
plugins {
    wepli("android.library")
    wepli("android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.wepli.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core:common"))
    implementation(project(":core:kotlin"))

    implementation(libs.retrofit)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.converter)
    implementation(libs.androidx.datastore.preferences)

    // Supabase
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest.kt)
    implementation(libs.supabase.storage.kt)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler.ksp)
}