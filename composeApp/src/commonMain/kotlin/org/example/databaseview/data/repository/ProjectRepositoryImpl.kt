package org.example.databaseview.data.repository

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.example.databaseview.data.dao.*
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.ProjectRepository

class ProjectRepositoryImpl(
    private val projectDao: ProjectDao,
    private val taskDao: TaskDao,
    private val contractDao: ContractDao,
    private val statusDao: StatusDao,
    private val workerDao: WorkerDao,
    private val clientDao: ClientDao
) : ProjectRepository {
    override suspend fun getProject(projectId: Int): Result<ProjectFullModel> {
        return Result.runCatching {
            dbQuery {
                val project =  projectDao.read(projectId) ?: throw Exception("Project not found")
                val tasks = getTasksForProject(project.id)
                val contract = getContractWithClient(project.contractId)
                ProjectFullModel(project, tasks, contract)
            }
        }
    }

    override fun getProjects(): Flow<List<ProjectFullModel>> = flow {
        var previousProjects: List<ProjectFullModel>? = null

        while (true) {
            val currentProjects = dbQuery {
                projectDao.readAll().map { project ->
                    val tasks = getTasksForProject(project.id)
                    val contract = getContractWithClient(project.contractId)
                    ProjectFullModel(project, tasks, contract)
                }
            }

            if (previousProjects == null || previousProjects != currentProjects) {
                emit(currentProjects)
                previousProjects = currentProjects
            }

            delay(5000)
        }
    }.flowOn(Dispatchers.IO)

    private fun getTasksForProject(projectId: Int): List<ProjectTaskModel> {
        return taskDao.readByProjectId(projectId).map { task ->
            val status = statusDao.read(task.statusId) ?: throw Exception("Status not found")
            val worker = workerDao.read(task.authorId) ?: throw Exception("Worker not found")
            ProjectTaskModel(task, status, worker)
        }
    }

    private fun getContractWithClient(contractId: Int): ContractClientModel {
        val contract = contractDao.read(contractId) ?: throw Exception("Contract not found")
        val client = clientDao.read(contract.clientId) ?: throw Exception("Client not found")
        return ContractClientModel(contract, client)
    }

    override suspend fun updateProject(project: Project) =
        Result.runCatching { dbQuery { projectDao.update(project) } }

    override suspend fun deleteProject(projectId: Int) =
        Result.runCatching { dbQuery { projectDao.delete(projectId) } }

    override suspend fun createProject(project: Project) =
        Result.runCatching { dbQuery { projectDao.create(project) } }
}