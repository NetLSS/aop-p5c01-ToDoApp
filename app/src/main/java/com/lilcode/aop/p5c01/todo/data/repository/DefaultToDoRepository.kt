package com.lilcode.aop.p5c01.todo.data.repository

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.data.local.db.dao.ToDoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultToDoRepository(
    private val toDoDao: ToDoDao,
    private val ioDispatcher: CoroutineDispatcher
): ToDoRepository {
    override suspend fun getToDoList(): List<ToDoEntity> = withContext(ioDispatcher) {
        toDoDao.getAll()
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? = withContext(ioDispatcher) {
        toDoDao.getById(itemId)
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>)= withContext(ioDispatcher) {
        toDoDao.insert(toDoList)
    }

    override suspend fun insertToDoItem(toDoItem: ToDoEntity):Long = withContext(ioDispatcher){
        toDoDao.insert(toDoItem)
    }

    override suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean= withContext(ioDispatcher) {
        toDoDao.update(toDoItem)
    }

    override suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    override suspend fun deleteToDoItem(id: Long): Boolean= withContext(ioDispatcher) {
        toDoDao.delete(id)
    }

}
