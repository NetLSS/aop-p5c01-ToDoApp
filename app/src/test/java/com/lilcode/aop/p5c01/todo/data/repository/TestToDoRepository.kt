package com.lilcode.aop.p5c01.todo.data.repository

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity

// 테스트 용도로 쓰일 레포지 토리

class TestToDoRepository: ToDoRepository {

    // for cache
    private val toDoList: MutableList<ToDoEntity> = mutableListOf()

    override suspend fun getToDoList(): List<ToDoEntity> {
        return toDoList
    }

    override suspend fun insertToDoItem(toDoItem: ToDoEntity):Long {
        this.toDoList.add(toDoItem)
        return toDoItem.id
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }

    override suspend fun updateToDoItem(toDoItem: ToDoEntity):Int {
        val foundToDoEntity = toDoList.find { it.id == toDoItem.id } ?: return -1


        this.toDoList[toDoList.indexOf(foundToDoEntity)] = toDoItem
        return 1


    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? {
        return toDoList.find { it.id == itemId }
    }

    override suspend fun deleteAll() {
        toDoList.clear()
    }

    override suspend fun deleteToDoItem(id: Long): Int {
        val foundToDoEntity = toDoList.find { it.id == id } ?: return -1
        this.toDoList.removeAt(toDoList.indexOf(foundToDoEntity))
        return 1
    }
}
