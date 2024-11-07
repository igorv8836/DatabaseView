package org.example.databaseview.data.repository

import kotlinx.coroutines.flow.*
import org.example.databaseview.data.dao.*
import org.example.databaseview.data.database.dbQuery
import org.example.databaseview.domain.model.*
import org.example.databaseview.domain.repository.ProjectRepository

class ProjectRepositoryImpl(
    private val projectDao: ProjectDao,
    private val taskDao: TaskDao,
    private val contractDao: ContractDao,
    private val statusDao: StatusDao
) : ProjectRepository {
    override fun getProjects(): Flow<List<ProjectFullModel>> = flow {
        val projects = dbQuery {
            val projects = projectDao.readAll()
            projects.map { project ->
                val tasks = taskDao.readByProjectId(project.id).map {
                    statusDao.read(it.statusId)?.let { status -> Pair(it, status) }
                        ?: throw Exception("Status not found")
                }
                val contract = contractDao.read(project.contractId)
                contract?.let {
                    ProjectFullModel(project, tasks, contract)
                } ?: throw Exception("Contract not found")
            }
        }
        emit(projects)
    }

    override suspend fun updateProject(project: Project) =
        Result.runCatching { projectDao.update(project) }

    override suspend fun deleteProject(projectId: Int) =
        Result.runCatching { projectDao.delete(projectId) }

    override suspend fun createProject(project: Project) =
        Result.runCatching { projectDao.create(project) }
}