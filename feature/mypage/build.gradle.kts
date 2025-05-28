plugins {
    wepli("android.feature")
    wepli("android.compose")
    wepli("android.hilt")
}

android {
    namespace = "com.wepli.feature.mypage"
}

dependencies {
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest.kt)

    implementation(libs.json.tree)
}