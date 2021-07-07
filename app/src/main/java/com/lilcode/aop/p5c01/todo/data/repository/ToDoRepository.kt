package com.lilcode.aop.p5c01.todo.data.repository

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity

/**
 * 1. insertToDoList
 * 2. getToDOList
 * 3. updateToDoItem
 */
interface ToDoRepository {

    suspend fun getToDoList():List<ToDoEntity>

    suspend fun insertToDoItem(toDoItem: ToDoEntity)

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

    suspend fun updateToDoItem(toDoItem: ToDoEntity): Boolean

    suspend fun getToDoItem(itemId: Long): ToDoEntity?

    suspend fun deleteAll()
}