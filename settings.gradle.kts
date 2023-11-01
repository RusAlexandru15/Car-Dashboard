pluginManagement {
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

        maven ( url =  "https://jitpack.io" )


    }
}

rootProject.name = "My Application 17.10"
include(":app")
 