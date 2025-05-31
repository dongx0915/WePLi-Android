import extensions.implementation
import java.util.Properties
import com.android.build.api.dsl.BuildType

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
val buildConfigProperties = properties.filterKeys { it != "sdk.dir" }

fun BuildType.addBuildConfigFields(props: Map<Any, Any>) {
    props.forEach { (key, value) ->
        buildConfigField("String", key.toString().uppercase(), "\"$value\"")
    }
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
            addBuildConfigFields(buildConfigProperties)
        }

        release {
            // local.properties 값 추가
            addBuildConfigFields(buildConfigProperties)
        }
    }
}

dependencies {
    implementation(project(":core:resources"))
    implementation(libs.gson)

    // Orbit
    implementation(libs.bundles.orbit)
}