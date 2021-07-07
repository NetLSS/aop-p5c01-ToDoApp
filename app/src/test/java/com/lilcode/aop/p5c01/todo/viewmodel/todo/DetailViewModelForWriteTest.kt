package com.lilcode.aop.p5c01.todo.viewmodel.todo

import com.lilcode.aop.p5c01.todo.ViewModelTest
import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailMode
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailViewModel
import com.lilcode.aop.p5c01.todo.presentation.detail.ToDoDetailState
import com.lilcode.aop.p5c01.todo.presentation.list.ListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject

/**
 * [DetailViewModel]을 테스트하기 위한 Unit Test Class
 *
 * 1. test viewModel fetch
 * 2. test insert todo
 */
@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteTest : ViewModelTest() {
    private val id = 0L

    private val detailViewModel by inject<DetailViewModel>{ parametersOf(DetailMode.WRITE, id) }
    private val listViewModel: ListViewModel by inject<ListViewModel>()

    private val todo = ToDoEntity(
        id=id,
        title="title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Test
    fun `test viewModel fetch`()= runBlockingTest{
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Write
            )
        )
    }
}