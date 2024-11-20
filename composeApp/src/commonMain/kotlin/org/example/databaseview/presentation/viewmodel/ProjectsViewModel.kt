package org.example.databaseview.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.*
import org.example.databaseview.utils.*

class ProjectsViewModel(
    private val projectsRepository: ProjectRepository,
    private val contractsRepository: ContractRepository,
    private val clientRepository: ClientRepository
) : ViewModel() {
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

    val contractClientModels = contractsRepository.getContracts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500_000),
        initialValue = emptyList()
    )

    val fullProject: MutableStateFlow<ProjectFullModel> = MutableStateFlow(ProjectFullModel())
    var project: Project
        get() = fullProject.value.project
        set(value) {
            fullProject.update { fullProject.value.copy(project = value) }
        }


    fun onEvent(event: ProjectsEvent) {
        when (event) {
            is ProjectsEvent.SaveProject -> {
                viewModelScope.launch {
                    projectsRepository.updateProject(project)
                }
            }

            is ProjectsEvent.CreateProject -> {
                viewModelScope.launch {
                    projectsRepository.createProject(project)
                }
            }

            is ProjectsEvent.DeleteProject -> {
                viewModelScope.launch {
                    projectsRepository.deleteProject(project.id)
                }
            }

            is ProjectsEvent.EnterProjectName -> {
                project = project.copy(name = event.newVal)
            }

            is ProjectsEvent.EnterProjectRequirements -> {
                project = project.copy(requirements = event.newVal)
            }

            is ProjectsEvent.DeleteContract -> TODO()
            ProjectsEvent.CreateNewContract -> TODO()
            is ProjectsEvent.EditContract -> TODO()
            is ProjectsEvent.SelectContract -> TODO()
            is ProjectsEvent.LoadProject -> {
                viewModelScope.launch {
                    val res = projectsRepository.getProject(event.id)
                    res.fold(
                        onSuccess = { newVal ->
                            fullProject.update { newVal }
                        },
                        onFailure = {
                            Napier.i { "Error: $it" }
                            TODO()
                        }
                    )
                }
            }
        }
    }
}


sealed interface ProjectsEvent {
    data object SaveProject : ProjectsEvent
    data class LoadProject(val id: Int) : ProjectsEvent
    data object CreateProject : ProjectsEvent
    data object DeleteProject : ProjectsEvent
    data object CreateNewContract : ProjectsEvent
    data class EnterProjectName(val newVal: String) : ProjectsEvent
    data class EnterProjectRequirements(val newVal: String) : ProjectsEvent
    class DeleteContract(id: Int) : ProjectsEvent
    class EditContract(selectedContract: Contract) : ProjectsEvent
    class SelectContract(contract: Contract) : ProjectsEvent
}


@Stable
sealed interface ProjectsScreenState {
    data class Success(val projects: List<ProjectFullModel>) : ProjectsScreenState
    data class Error(val message: String) : ProjectsScreenState
    data object Loading : ProjectsScreenState
}