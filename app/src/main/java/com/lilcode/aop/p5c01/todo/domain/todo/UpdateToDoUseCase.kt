package com.lilcode.aop.p5c01.todo.domain.todo

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.data.repository.ToDoRepository
import com.lilcode.aop.p5c01.todo.domain.UseCase

internal class UpdateToDoUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(toDoEntity: ToDoEntity): Int{
        return toDoRepository.updateToDoItem(toDoEntity)
    }
}