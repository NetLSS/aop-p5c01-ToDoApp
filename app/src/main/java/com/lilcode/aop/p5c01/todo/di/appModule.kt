package com.lilcode.aop.p5c01.todo.di

import android.content.Context
import androidx.room.Room
import com.lilcode.aop.p5c01.todo.data.local.db.ToDoDatabase
import com.lilcode.aop.p5c01.todo.data.repository.DefaultToDoRepository
import com.lilcode.aop.p5c01.todo.data.repository.ToDoRepository
import com.lilcode.aop.p5c01.todo.domain.todo.*
import com.lilcode.aop.p5c01.todo.domain.todo.DeleteAllToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.DeleteToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.GetToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.GetToDoListUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.InsertToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.InsertToDoListUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.UpdateToDoUseCase
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailMode
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailViewModel
import com.lilcode.aop.p5c01.todo.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import kotlin.math.sin

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // viewModel
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel{ (detailMode: DetailMode, id:Long) ->
        DetailViewModel(
            detailMode=detailMode,
            id = id,
            get(),
            get(),
            get(),
            get()
        )
    }

    // UseCase
    factory { GetToDoListUseCase(get())    }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }

    // Repository
    single<ToDoRepository> { DefaultToDoRepository(get(),get()) } // ToDoRepository 를 반환

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get())}
}

internal fun provideDB(context: Context): ToDoDatabase=
    Room.databaseBuilder(context, ToDoDatabase::class.java,ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()