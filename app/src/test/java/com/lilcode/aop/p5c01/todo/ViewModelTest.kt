package com.lilcode.aop.p5c01.todo

import android.app.Application
import androidx.lifecycle.LiveData
import com.lilcode.aop.p5c01.todo.di.appTestModule
import com.lilcode.aop.p5c01.todo.liveData.LiveDataTestObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi // Dispatchers 사용시 워닝 제거.
internal class ViewModelTest: KoinTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()// 유닛 테스트를 위한 목킹 라이브러리

    @Mock
    private lateinit var context: Application

    // 쓰레드를 쉽게 바꿀 수 있도록 디스패쳐 정의
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        startKoin{
            androidContext(context)
            modules(appTestModule)
        }
        Dispatchers.setMain(dispatcher) // 코루틴 디스패쳐 사용가능 하도록.
    }

    @After
    fun tearDown(){
        stopKoin() // 메모리 해제
        Dispatchers.resetMain() // MainDispatcher를 초기화 해주어야 메모리 누수가 발생하지 않음.
    }

    protected fun <T> LiveData<T>.test(): LiveDataTestObserver<T>{
        val testObserver = LiveDataTestObserver<T>()
        observeForever(testObserver)
        return testObserver
    }
}