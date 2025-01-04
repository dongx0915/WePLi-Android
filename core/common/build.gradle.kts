import java.util.Properties

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

plugins {
    wepli("android.library")
    wepli("android.compose")
}

android {
    namespace = "com.wepli.core.common"

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            // local.properties 값 추가
            properties.filter { it.key != "sdk.dir" }.forEach { (key, value) ->
                buildConfigField("String", key.toString().uppercase(), "\"$value\"")
            }
        }

        release {
            // local.properties 값 추가
            properties.filter { it.key != "sdk.dir" }.forEach { (key, value) ->
                buildConfigField("String", key.toString().uppercase(), "\"$value\"")
            }
        }
    }
}

dependencies {
    implementation(libs.gson)

    // Orbit
    implementation(libs.bundles.orbit)
}