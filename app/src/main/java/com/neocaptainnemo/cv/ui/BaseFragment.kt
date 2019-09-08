package com.neocaptainnemo.cv.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment(@LayoutRes val layout: Int) : Fragment(layout) {

    private val compositeDisposable = CompositeDisposable()

    fun autoDispose(disposable: (() -> Disposable)) {
        compositeDisposable.add(disposable())
    }

    fun autoDispose(vararg disposables: (() -> Disposable)) {
        disposables.forEach {
            compositeDisposable.add(it())
        }
    }


    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
}