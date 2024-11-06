package org.example.databaseview.data.di

import org.example.databaseview.data.database.MainDatabase
import org.koin.dsl.module

fun dataModule() = module {
    includes(databaseModule(), daoModule())
    single { MainDatabase() }
}