package com.lilcode.aop.p5c01.todo.di

import com.lilcode.aop.p5c01.todo.data.repository.TestToDoRepository
import com.lilcode.aop.p5c01.todo.data.repository.ToDoRepository
import com.lilcode.aop.p5c01.todo.domain.todo.GetToDoListUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.InsertToDoListUseCase
import com.lilcode.aop.p5c01.todo.presentation.list.ListViewModel
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    // viewModel
    viewModel { ListViewModel(get())}

    // UseCase
    factory { GetToDoListUseCase(get())    }
    factory { InsertToDoListUseCase(get()) }

    // Repository
    single<ToDoRepository> { TestToDoRepository() } // ToDoRepository 를 반환
}