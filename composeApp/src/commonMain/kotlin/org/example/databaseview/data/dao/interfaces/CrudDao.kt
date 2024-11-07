package org.example.databaseview.data.dao.interfaces

interface CrudDao<T> {
    fun create(entity: T): Boolean
    fun read(id: Int): T?
    fun readAll(): List<T>
    fun update(entity: T): Boolean
    fun delete(id: Int): Boolean
}
