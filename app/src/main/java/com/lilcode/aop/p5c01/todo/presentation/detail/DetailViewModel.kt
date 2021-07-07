package com.lilcode.aop.p5c01.todo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lilcode.aop.p5c01.todo.domain.todo.GetToDoItemUseCase
import com.lilcode.aop.p5c01.todo.presentation.BaseViewModel
import com.lilcode.aop.p5c01.todo.presentation.list.ToDoListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id:Long = -1,
    private val getToDoItemUseCase: GetToDoItemUseCase
): BaseViewModel() {

    private var _toDoDetailLiveData = MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val todoDetailLiveData: LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch{
        when(detailMode){
            DetailMode.WRITE->{
                // TODO 나중에 작성 모드로 상세화면 진입 로직 처리
            }
            DetailMode.DETAIL->{
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
                try {
                    getToDoItemUseCase(id)?.let{
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(it))

                    }?:kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)

                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }
}