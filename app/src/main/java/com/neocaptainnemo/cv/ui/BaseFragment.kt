package com.neocaptainnemo.cv.ui

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()

    fun autoDispose(disposable: (() -> Disposable)) {
        compositeDisposable.add(disposable.invoke())
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

}