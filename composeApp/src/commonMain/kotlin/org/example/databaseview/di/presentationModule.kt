package org.example.databaseview.di

import org.example.databaseview.presentation.viewmodel.ProjectsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun presentationModule() = module {
    viewModel { ProjectsViewModel(get()) }
}