package org.example.databaseview

import androidx.compose.ui.window.*
import org.example.databaseview.di.KoinFactory
import org.example.databaseview.presentation.App

fun main() = application {
    KoinFactory.setupKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "DatabaseView",
    ) {
        App()
    }
}