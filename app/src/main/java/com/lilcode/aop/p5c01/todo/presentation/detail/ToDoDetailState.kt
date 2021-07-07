package com.lilcode.aop.p5c01.todo.presentation.detail

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity

sealed class ToDoDetailState {
    object UnInitialized : ToDoDetailState()

    object Loading : ToDoDetailState()

    data class Success (
        val toDoItem: ToDoEntity
    ): ToDoDetailState()

    object Delete: ToDoDetailState()

    object Modify: ToDoDetailState()

    object Error: ToDoDetailState()

    object Write: ToDoDetailState()
}