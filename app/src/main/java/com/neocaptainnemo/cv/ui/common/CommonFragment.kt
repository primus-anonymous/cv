package com.neocaptainnemo.cv.ui.common

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.daggerInject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_common.*
import javax.inject.Inject


class CommonFragment : androidx.fragment.app.Fragment() {

    @Inject
    lateinit var adapter: CommonAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CommonViewModel

    private val compositeDisposable = CompositeDisposable()


    override fun onAttach(context: Context?) {

        daggerInject()

        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CommonViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        commonList.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_common, container, false)

    override fun onStart() {
        super.onStart()


        compositeDisposable.add(viewModel.progress().subscribe {
            commonsProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(viewModel.empty().subscribe {
            commonsEmpty.visibility = if (it) View.VISIBLE else View.GONE
        })

        compositeDisposable.add(viewModel.commons().subscribe({

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
