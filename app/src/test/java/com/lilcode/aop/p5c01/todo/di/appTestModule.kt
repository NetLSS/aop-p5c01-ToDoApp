package com.lilcode.aop.p5c01.todo.di

import com.lilcode.aop.p5c01.todo.data.repository.TestToDoRepository
import com.lilcode.aop.p5c01.todo.data.repository.ToDoRepository
import com.lilcode.aop.p5c01.todo.domain.todo.*
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailMode
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailViewModel
import com.lilcode.aop.p5c01.todo.presentation.list.ListViewModel
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    // viewModel
    viewModel { ListViewModel(get(), get(), get())}
    viewModel{ (detailMode: DetailMode, id:Long) ->
        DetailViewModel(
            detailMode=detailMode,
            id = id,
            get(),
            get(),
            get(),
            get()
        )}

    // UseCase
    factory { GetToDoListUseCase(get())    }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }

    // Repository
    single<ToDoRepository> { TestToDoRepository() } // ToDoRepository 를 반환
}