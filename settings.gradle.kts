pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Matrix"
include(":app")
//include(":core:common")
//include(":core:database")
//include(":core:matrix")
//include(":core:network")
//include(":core:model")
//include(":feature:login")
include(":core:common")
include(":feature:auth")
include(":core:matrix")
include(":core:data")
include(":feature:user")
include(":feature:main")
include(":core:network")
include(":feature:chat")
include(":feature:room")
