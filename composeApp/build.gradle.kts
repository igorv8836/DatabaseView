import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.hot.reload)
}

composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
    featureFlags.add(ComposeFeatureFlag.StrongSkipping)
    featureFlags.add(ComposeFeatureFlag.IntrinsicRemember)
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("org.example.databaseview.MainKt")
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.uiTooling)

            implementation(libs.navigation.compose)

            implementation(libs.napier)

            implementation(libs.koin.core)
            implementation(libs.koin.composeVM)
            implementation(libs.koin.compose)

            implementation(libs.androidx.lifecycle.viewmodel)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.exposed.core)
            implementation(libs.exposed.dao)
            implementation(libs.exposed.jdbc)
            implementation(libs.exposed.java.time)
            implementation(libs.postgresql)
            implementation("com.typesafe:config:1.4.2")

            implementation(libs.orbit.core)


            implementation(project(":orbit_mvi"))
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.example.databaseview.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.databaseview"
            packageVersion = "1.0.0"
        }
    }
}
