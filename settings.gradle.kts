enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    // build-logic은 전역적으로 관리
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WePLi"
// app 모듈에는 다른 모듈 include만 추가
include(":app")

/**
 *  아래와 같이 선언하면 서브 모듈이 되는 것이므로
 *  다른 모듈에서 build.gradle에 build-logic을 매번 추가해줘야함
 *  include(":build-logic") // remove
 *  include(":build-logic:convention") // remove
 */
// App 모듈에도 모든 모듈에 대해 참조 설정 해주어야함
include(":feature:main")
include(":feature:home")
include(":feature:community")
include(":core")
include(":core:common")
include(":core:navigator")
include(":core:kotlin")
include(":designsystem")
include(":domain")
include(":data")
include(":shared:feature")
