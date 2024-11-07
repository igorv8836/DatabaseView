package org.example.databaseview.data.dao.interfaces

interface CompositeCrudDao<T, K1, K2> {
    fun create(entity: T): Boolean
    fun readByCompositeKey(key1: K1, key2: K2): T?
    fun readAll(): List<T>
    fun updateByCompositeKey(entity: T, key1: K1, key2: K2): Boolean
    fun deleteByCompositeKey(key1: K1, key2: K2): Boolean
}
