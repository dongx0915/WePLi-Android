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
include(":app")

/**
 *  아래와 같이 선언하면 서브 모듈이 되는 것이므로
 *  다른 모듈에서 build.gradle에 build-logic을 매번 추가해줘야함
 *  include(":build-logic") // remove
 *  include(":build-logic:convention") // remove
 */