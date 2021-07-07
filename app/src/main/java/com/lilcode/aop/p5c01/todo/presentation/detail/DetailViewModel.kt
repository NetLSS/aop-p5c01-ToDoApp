package com.lilcode.aop.p5c01.todo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.domain.todo.DeleteToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.GetToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.InsertToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.UpdateToDoUseCase
import com.lilcode.aop.p5c01.todo.presentation.BaseViewModel
import com.lilcode.aop.p5c01.todo.presentation.list.ToDoListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1,
    private val getToDoItemUseCase: GetToDoItemUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) : BaseViewModel() {

    private var _toDoDetailLiveData =
        MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val todoDetailLiveData: LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        when (detailMode) {
            DetailMode.WRITE -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Write)
            }
            DetailMode.DETAIL -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)

                try {
                    getToDoItemUseCase(id)?.let {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(it))

                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }

    fun deleteTodo() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
            if (deleteToDoItemUseCase(id)==1) {
                _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
            } else {
                _toDoDetailLiveData.postValue(ToDoDetailState.Error)

            }
        } catch (e: Exception) {
            e.printStackTrace()
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun writeToDo(title: String, description: String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        when(detailMode){
            DetailMode.WRITE -> {
                try {
                    val toDoEntity = ToDoEntity(
                        title = title,
                        description = description
                    )
                    id = insertToDoItemUseCase(toDoEntity)
                    _toDoDetailLiveData.postValue(ToDoDetailState.Success(toDoEntity))
                    detailMode = DetailMode.DETAIL
                }catch (e: Exception){
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)

                }
            }
            DetailMode.DETAIL -> {
                try {
                    getToDoItemUseCase(id)?.let {
                        val updateToDoEntity = it.copy(
                            title=title,
                            description = description
                        )
                        updateToDoUseCase(updateToDoEntity)
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(updateToDoEntity))

                    }?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)

                    }
                }catch (e:Exception){
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)

                }
            }
        }
    }
}