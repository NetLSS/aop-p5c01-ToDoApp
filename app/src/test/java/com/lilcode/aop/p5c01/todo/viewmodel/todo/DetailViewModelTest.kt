package com.lilcode.aop.p5c01.todo.viewmodel.todo

import com.lilcode.aop.p5c01.todo.ViewModelTest
import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.domain.todo.InsertToDoItemUseCase
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailMode
import com.lilcode.aop.p5c01.todo.presentation.detail.DetailViewModel
import com.lilcode.aop.p5c01.todo.presentation.detail.ToDoDetailState
import com.lilcode.aop.p5c01.todo.presentation.list.ListViewModel
import com.lilcode.aop.p5c01.todo.presentation.list.ToDoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.test.inject
import java.lang.Exception

/**
 * [DetailViewModel]을 테스트하기 위한 Unit Test Class
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test delete todo
 * 4. test update todo
 */
@ExperimentalCoroutinesApi
internal class DetailViewModelTest: ViewModelTest() {

    private val id = 1L
    private val detailViewModel by inject<DetailViewModel>{ parametersOf(DetailMode.DETAIL, id) }
    private val listViewModel: ListViewModel by inject<ListViewModel>()

    private val insertToDoItemUseCase: InsertToDoItemUseCase by inject()
    private val todo = ToDoEntity(
        id=id,
        title="title $id",
        description = "description $id",
        hasCompleted = false
    )

    @Before
    fun init(){
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoItemUseCase(todo)
    }

    @Test
    fun ` test viewModel fetch`() = runBlockingTest{
        val testObservable = detailViewModel.todoDetailLiveData.test()
        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun `test delete todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.todoDetailLiveData.test()

        detailViewModel.deleteTodo()

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialized,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )

        // 메인에서도 데이터가 없어야함
        val listTestObservable = listViewModel.todoListLiveData.test()
        listViewModel.fetchData() // 데이터를 불러옴(온리쥼 탈때)
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Suceess(listOf())
            )
        )



    }
}