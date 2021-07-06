package com.lilcode.aop.p5c01.todo.data.repository

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity

/**
 * 1. insertToDoList
 * 2. getToDOList
 */
interface ToDoRepository {

    suspend fun getToDoList():List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)
}