package org.example.databaseview.data.di

import org.example.databaseview.data.dao.*
import org.koin.dsl.module

fun daoModule() = module {
    single { StatusDao() }
    single { ContractDao() }
    single { DepartmentDao() }
    single { License1CDao() }
    single { PositionDao() }
    single { ProjectDao() }
    single { StatusDao() }
    single { TaskDao() }
    single { TaskWorkerDao() }
    single { WorkerDao() }
}