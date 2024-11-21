package org.example.databaseview.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import org.example.databaseview.domain.model.ProjectFullModel
import org.example.databaseview.domain.repository.ProjectRepository
import org.example.databaseview.utils.*

class ProjectsViewModel(
    projectsRepository: ProjectRepository
) : ViewModel() {
    val state = projectsRepository.getProjects().asResult().map {
        when (it) {
            is MyResult.Success -> ProjectsScreenState.Success(it.data.sortedBy { pr -> pr.project.name.lowercase() })
            is MyResult.Error -> ProjectsScreenState.Error(it.exception.message ?: "Unknown error")
            is MyResult.Loading -> ProjectsScreenState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ProjectsScreenState.Loading
    )
}


@Stable
sealed interface ProjectsScreenState {
    data class Success(val projects: List<ProjectFullModel>) : ProjectsScreenState
    data class Error(val message: String) : ProjectsScreenState
    data object Loading : ProjectsScreenState
}