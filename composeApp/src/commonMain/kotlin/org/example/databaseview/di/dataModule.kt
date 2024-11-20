package org.example.databaseview.di

import org.example.databaseview.data.database.MainDatabase
import org.example.databaseview.data.repository.*
import org.example.databaseview.domain.repository.*
import org.koin.dsl.module

fun dataModule() = module {
    includes(databaseModule(), daoModule())
    single { MainDatabase() }

    single<ProjectRepository> { ProjectRepositoryImpl(get(), get(), get(), get()) }
    single<ContractRepository> { ContractRepositoryImpl(get(), get()) }
}