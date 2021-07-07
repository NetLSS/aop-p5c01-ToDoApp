package com.lilcode.aop.p5c01.todo.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

// 공통으로 쓸 수 있는 패치 데이터
internal abstract class BaseViewModel: ViewModel() {
    abstract fun fetchData(): Job
}