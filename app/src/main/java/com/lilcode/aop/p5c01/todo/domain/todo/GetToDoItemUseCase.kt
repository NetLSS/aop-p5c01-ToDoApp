package com.lilcode.aop.p5c01.todo.domain.todo

import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.data.repository.ToDoRepository
import com.lilcode.aop.p5c01.todo.domain.UseCase

internal class GetToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {
    suspend operator fun invoke(itemId: Long):ToDoEntity?{
        return toDoRepository.getToDoItem(itemId)
    }
}