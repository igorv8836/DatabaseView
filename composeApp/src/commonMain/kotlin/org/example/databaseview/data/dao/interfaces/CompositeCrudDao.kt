package org.example.databaseview.data.dao.interfaces

interface CompositeCrudDao<T, K1, K2> {
    suspend fun create(entity: T): Boolean
    suspend fun readByCompositeKey(key1: K1, key2: K2): T?
    suspend fun readAll(): List<T>
    suspend fun updateByCompositeKey(entity: T, key1: K1, key2: K2): Boolean
    suspend fun deleteByCompositeKey(key1: K1, key2: K2): Boolean
}
