package org.example.databaseview.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.databaseview.domain.model.*

interface ProjectRepository {
    suspend fun getProject(projectId: Int): Result<ProjectFullModel>
    fun getProjects(): Flow<List<ProjectFullModel>>
    suspend fun updateProject(project: Project): Result<Boolean>
    suspend fun deleteProject(projectId: Int): Result<Boolean>
    suspend fun createProject(project: Project): Result<Boolean>
}