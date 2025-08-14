pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://download.jetbrains.com/teamcity-repository")
        maven("https://kotlin.bintray.com/kotlin-native")
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

rootProject.name = "LearningPartnerMobile"
include(":shared")
include(":androidApp") 