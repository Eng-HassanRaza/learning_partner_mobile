plugins {
    kotlin("multiplatform") version "1.9.20" apply false
    kotlin("android") version "1.9.20" apply false
    kotlin("plugin.serialization") version "1.9.20" apply false
    id("com.android.application") version "8.1.4" apply false
    id("com.android.library") version "8.1.4" apply false
    id("org.jetbrains.compose") version "1.5.10" apply false
    id("io.realm.kotlin") version "1.11.0" apply false
}

// Repository configuration moved to settings.gradle.kts

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
} 