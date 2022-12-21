package com.geekbrains.myfirsttests.presenter.details

import android.view.View
import com.geekbrains.myfirsttests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private val viewContract: ViewDetailsContract,
    private var count: Int = 0
) : PresenterDetailsContract {

    private var view: View? = null

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract.setCount(count)
    }

    override fun onAttach(view: View) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }
}