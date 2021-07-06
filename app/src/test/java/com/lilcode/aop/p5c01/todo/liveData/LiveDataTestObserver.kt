package com.lilcode.aop.p5c01.todo.liveData

import androidx.lifecycle.Observer

class LiveDataTestObserver<T> : Observer<T> {

    private val values: MutableList<T> = mutableListOf()

    override fun onChanged(t: T) {
        values.add(t)
    }

    // 실제 값과 예상 값 비교
    fun assertValueSequence(sequence: List<T>): LiveDataTestObserver<T> {
        var i = 0
        val actualIterator = values.iterator()
        val expectedIterator = sequence.iterator()

        var actualNext: Boolean
        var expectedNext: Boolean

        while (true) {
            actualNext = actualIterator.hasNext()
            expectedNext = expectedIterator.hasNext()

            if (!actualNext || !expectedNext)
                break

            val actual: T = actualIterator.next()
            val expected: T = actualIterator.next()

            if (actual != expected) {
                throw AssertionError("actual: ${actual}, expected: ${expected}, index: $i")
            }
            i++
        }

        if (actualNext){ // 실제 데이터가 더 많은 경우;
            throw AssertionError("More values received than expected ($i)")
        }
        if(expectedNext){
            throw AssertionError("Fewer values received than expected ($i)")
        }

        return this
    }
}