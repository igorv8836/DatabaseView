package org.example.databaseview.di

import org.example.databaseview.data.database.MainDatabase
import org.koin.dsl.module

fun databaseModule() = module {
    single { MainDatabase() }
}