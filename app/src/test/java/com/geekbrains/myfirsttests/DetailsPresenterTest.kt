package com.geekbrains.myfirsttests

import com.geekbrains.myfirsttests.presenter.details.DetailsPresenter
import com.geekbrains.myfirsttests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    private var count: Int = 0

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter(viewContract, count)
    }

    @Test
    fun setCounter_Test() {
        val counter = 0
        assertEquals(counter, count)
    }

    @Test
    fun onIncrement_Test() {
        count++
        viewContract.setCount(count)
        verify(viewContract, times(1)).setCount(count)
        assertEquals(1, count)
    }

    @Test
    fun onDecrement_Test() {
        count--
        viewContract.setCount(count)
        verify(viewContract, times(1)).setCount(count)
        assertEquals(-1, count)
    }

    @Test
    fun onAttach_Test() {
        //...
    }

    @Test
    fun onDetach_Test() {
        //...
    }
}