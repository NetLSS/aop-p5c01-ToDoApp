package com.lilcode.aop.p5c01.todo.viewmodel.todo

import com.lilcode.aop.p5c01.todo.ViewModelTest
import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import com.lilcode.aop.p5c01.todo.domain.todo.GetToDoItemUseCase
import com.lilcode.aop.p5c01.todo.domain.todo.InsertToDoListUseCase
import com.lilcode.aop.p5c01.todo.presentation.list.ListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

/**
 * [ListViewModel]을 테스트하기 위한 unit Test class
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Item Update
 * 4. test Item Delete All
 */

@ExperimentalCoroutinesApi
internal class ListViewModelTest: ViewModelTest() {

    // 주입 받기;
    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()

    private val mockList = (0 until 10).map {
        ToDoEntity(
            id=it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }
    /**
     * 필요한 use case 들
     *  1. InsertToDoListUseCase
     *  2. GetTODoItemUseCase
     */

    @Before
    fun init(){
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    // Test : 입력된 데이터를 불러와서 검증한다.
    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest{
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                mockList
            )
        )
    }

    // Test : 데이터를 업데이트 했을 때 잘 반영되는가
    @Test
    fun `test Item Update`():Unit = runBlockingTest {
        val todo = ToDoEntity(
            id=1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert(getToDoItemUseCase(todo.id)?.hasCompleted?:false == todo.hasCompleted)
    }

    // Test: 데이터 전체 삭제 시 빈 상태로 보여지는가
    @Test
    fun `test Item Delete All`():Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()

        viewModel.fetchData()

        viewModel.deleteAll()

        testObservable.assertValueSequence(
            listOf(
                mockList,
                listOf()
            )
        )
    }

}