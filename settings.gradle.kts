pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Add this to resolve the Kotlin plugin
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidCA"
include(":app")
