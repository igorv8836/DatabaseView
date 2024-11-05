package org.example.databaseview.di

import org.example.databaseview.TestViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun testModule() = module {
    viewModel { TestViewModel() }
}