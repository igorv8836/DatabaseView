plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

kotlin {
    jvm("desktop")
    sourceSets {
        commonMain.dependencies {
            implementation(libs.atomicfu)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.orbit.core)

            implementation(compose.runtime)
            implementation(libs.navigation.compose)
        }
        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}