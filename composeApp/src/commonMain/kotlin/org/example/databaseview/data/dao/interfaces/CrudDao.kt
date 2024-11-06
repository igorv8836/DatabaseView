package org.example.databaseview.data.dao.interfaces

interface CrudDao<T> {
    suspend fun create(entity: T): Boolean
    suspend fun read(id: Int): T?
    suspend fun readAll(): List<T>
    suspend fun update(entity: T): Boolean
    suspend fun delete(id: Int): Boolean
}
