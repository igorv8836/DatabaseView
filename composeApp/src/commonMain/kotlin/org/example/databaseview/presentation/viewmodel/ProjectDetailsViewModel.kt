package org.example.databaseview.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.example.orbit_mvi.viewmodel.container
import io.github.aakira.napier.Napier
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.*
import org.orbitmvi.orbit.ContainerHost

class ProjectDetailsViewModel(
    private val projectsRepository: ProjectRepository,
    private val contractsRepository: ContractRepository
) : ContainerHost<ProjectDetailsScreenState, ProjectDetailsEffect>, ViewModel() {
    override val container =
        container<ProjectDetailsScreenState, ProjectDetailsEffect>(ProjectDetailsScreenState()){
            contractsRepository.getContracts().collect{
                reduce { state.copy(contracts = it) }
            }
        }
    private val project: Project
        get() = container.stateFlow.value.expandedProject.project


    fun onEvent(event: ProjectDetailsEvent) {
        when (event) {
            is ProjectDetailsEvent.SaveProject -> {
                intent {
                    projectsRepository.updateProject(project).exceptionOrNull()?.let {
                        postSideEffect(ProjectDetailsEffect.ShowError(it.message ?: "Unknown error"))
                    } ?: run { postSideEffect(ProjectDetailsEffect.NavigateToLastScreen) }
                }
            }

            is ProjectDetailsEvent.CreateProject -> {
                intent {
                    projectsRepository.createProject(project).exceptionOrNull()?.let {
                        postSideEffect(ProjectDetailsEffect.ShowError(it.message ?: "Unknown error"))
                    } ?: run { postSideEffect(ProjectDetailsEffect.NavigateToLastScreen) }
                }
            }

            is ProjectDetailsEvent.DeleteProject -> {
                intent {
                    projectsRepository.deleteProject(project.id).exceptionOrNull()?.let {
                        postSideEffect(ProjectDetailsEffect.ShowError(it.message ?: "Unknown error"))
                    } ?: run { postSideEffect(ProjectDetailsEffect.NavigateToLastScreen) }
                }
            }

            is ProjectDetailsEvent.EnterProjectName -> {
                blockingIntent {
                    reduce {
                        state.copy(
                            expandedProject = state.expandedProject.copy(
                                project = project.copy(name = event.newVal)
                            )
                        )
                    }
                }
            }

            is ProjectDetailsEvent.EnterProjectRequirements -> {
                blockingIntent {
                    reduce {
                        state.copy(
                            expandedProject = state.expandedProject.copy(
                                project = project.copy(requirements = event.newVal)
                            )
                        )
                    }
                }
            }

            is ProjectDetailsEvent.SelectContract -> {
                intent {
                    reduce {
                        state.copy(
                            expandedProject = state.expandedProject.copy(
                                contract = event.contract,
                                project = project.copy(contractId = event.contract.contract.id)
                            )
                        )
                    }
                }
            }

            is ProjectDetailsEvent.LoadProject -> {
                intent {
                    if (event.id == null) {
                        reduce { state.copy(isLoading = false) }
                        return@intent
                    }
                    reduce { state.copy(isLoading = true) }
                    val res = projectsRepository.getProject(event.id)
                    res.fold(onSuccess = { newVal ->
                        reduce { state.copy(expandedProject = newVal, isLoading = false) }
                    }, onFailure = {
                        Napier.i { "Error: $it" }
                        reduce { state.copy(isLoading = false) }
                        postSideEffect(ProjectDetailsEffect.ShowError("Error: $it"))
                    })
                }
            }
        }
    }
}


sealed interface ProjectDetailsEvent {
    data object SaveProject : ProjectDetailsEvent
    data class LoadProject(val id: Int?) : ProjectDetailsEvent // creating or loading existing project
    data object CreateProject : ProjectDetailsEvent
    data object DeleteProject : ProjectDetailsEvent
    data class EnterProjectName(val newVal: String) : ProjectDetailsEvent
    data class EnterProjectRequirements(val newVal: String) : ProjectDetailsEvent
    data class SelectContract(val contract: ContractClientModel) : ProjectDetailsEvent
}


@Immutable
data class ProjectDetailsScreenState(
    val isLoading: Boolean = true,
    val expandedProject: ProjectFullModel = ProjectFullModel(),
    val contracts: List<ContractClientModel> = emptyList()
)

sealed interface ProjectDetailsEffect {
    data class ShowError(val message: String) : ProjectDetailsEffect
    data object NavigateToLastScreen : ProjectDetailsEffect
}
