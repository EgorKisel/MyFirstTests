package com.geekbrains.myfirsttests.presenter

import android.view.View

internal interface PresenterContract {
    fun onAttach(view: View)
    fun onDetach()
}