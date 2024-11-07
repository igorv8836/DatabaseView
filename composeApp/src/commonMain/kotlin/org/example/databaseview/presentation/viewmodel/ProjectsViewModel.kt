package org.example.databaseview.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import org.example.databaseview.domain.model.ProjectFullModel
import org.example.databaseview.domain.repository.ProjectRepository
import org.example.databaseview.utils.*

class ProjectsViewModel(
    private val projectsRepository: ProjectRepository
): ViewModel() {
    val state = projectsRepository.getProjects().asResult().map {
        when (it) {
            is MyResult.Success -> ProjectsScreenState.Success(it.data)
            is MyResult.Error -> ProjectsScreenState.Error(it.exception.message ?: "Unknown error")
            is MyResult.Loading -> ProjectsScreenState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProjectsScreenState.Loading
    )
}


@Stable
interface ProjectsScreenState {
    data class Success(val projects: List<ProjectFullModel>): ProjectsScreenState
    data class Error(val message: String): ProjectsScreenState
    object Loading: ProjectsScreenState
}