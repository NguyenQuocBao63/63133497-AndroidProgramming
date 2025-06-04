pluginManagement {
    repositories {
        gradlePluginPortal() 
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // ... các repo khác nếu có
    }
}

rootProject.name = "Online Exams"
include(":app")