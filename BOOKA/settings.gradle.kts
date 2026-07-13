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
    }
}

rootProject.name = "BOOKA"

include(":app")

include(":core:common")
include(":core:model")
include(":core:designsystem")
include(":core:ui")
include(":core:navigation")
include(":core:database")
include(":core:datastore")
include(":core:files")
include(":core:network")
include(":core:security")
include(":core:testing")

include(":domain")

include(":data:library")
include(":data:import")
include(":data:metadata")
include(":data:reader")

include(":feature:library")
include(":feature:import")
include(":feature:metadata")
include(":feature:reader-novel")
include(":feature:reader-comic")
include(":feature:settings")
