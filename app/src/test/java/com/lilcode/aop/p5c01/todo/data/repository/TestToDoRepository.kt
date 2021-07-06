package com.lilcode.aop.p5c01.todo.data.repository

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity

// 테스트 용도로 쓰일 레포지 토리

class TestToDoRepository: ToDoRepository {

    // for cache
    private val toDoList: MutableList<ToDoEntity> = mutableListOf()

    override suspend fun getToDoList(): List<ToDoEntity> {
        return toDoList
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }
}
