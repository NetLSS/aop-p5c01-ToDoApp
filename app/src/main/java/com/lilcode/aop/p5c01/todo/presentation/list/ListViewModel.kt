package com.lilcode.aop.p5c01.todo.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.domain.todo.DeleteAllToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.GetToDoListUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.UpdateToDoUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 필요한 use case
 * 1. [GetToDoListUseCase]
 * 2. [UpdateToDoUseCase]
 * 3. [DeleteAllToDoItemUseCase]
 */

internal class ListViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val deleteAllToDoItemUseCase: DeleteAllToDoItemUseCase
) : ViewModel() {
    private var _toDoListLiveData = MutableLiveData<List<ToDoEntity>>()
    val todoListLiveData: LiveData<List<ToDoEntity>> = _toDoListLiveData

    fun fetchData(): Job = viewModelScope.launch {
        _toDoListLiveData.postValue(getToDoListUseCase())
    }

    fun updateEntity(toDoEntity: ToDoEntity) = viewModelScope.launch{
        updateToDoUseCase(toDoEntity)
    }

    fun deleteAll() = viewModelScope.launch{
        deleteAllToDoItemUseCase()
        _toDoListLiveData.postValue(listOf())
    }


}