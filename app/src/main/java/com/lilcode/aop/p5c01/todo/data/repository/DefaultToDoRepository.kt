package com.lilcode.aop.p5c01.todo.data.repository

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity

class DefaultToDoRepository: ToDoRepository {
    override suspend fun getToDoList(): List<ToDoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        TODO("Not yet implemented")
    }
}
