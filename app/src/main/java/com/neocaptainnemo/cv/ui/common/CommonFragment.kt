package com.neocaptainnemo.cv.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.neocaptainnemo.cv.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_common.*
import org.koin.android.viewmodel.ext.android.viewModel


class CommonFragment : androidx.fragment.app.Fragment() {

    private val adapter: CommonAdapter = CommonAdapter()

    private val vModel: CommonViewModel by viewModel()

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonList.layoutManager = LinearLayoutManager(context)
        commonList.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_common, container, false)

    override fun onStart() {
        super.onStart()


        compositeDisposable.add(vModel.progress().subscribe {
            commonsProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(vModel.empty().subscribe {
            commonsEmpty.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(vModel.commons().subscribe({

            adapter.clear()
            adapter.add(it)

            adapter.notifyDataSetChanged()
        }, {

        }))


    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()
    }

    companion object {

        const val tag = "CommonFragment"

        fun instance(): CommonFragment = CommonFragment()
    }
}
