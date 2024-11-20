package org.example.databaseview

import androidx.compose.ui.window.*
import io.github.aakira.napier.*
import org.example.databaseview.data.database.MainDatabase
import org.example.databaseview.di.KoinFactory
import org.example.databaseview.presentation.App
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() = application {
    Napier.base(DebugAntilog())
    KoinFactory.setupKoin()
    KoinFactory.getDI().get<MainDatabase>().connect()
    Window(
        onCloseRequest = ::exitApplication,
        title = "DatabaseView",
    ) {
        DevelopmentEntryPoint {
            App()
        }
    }
}