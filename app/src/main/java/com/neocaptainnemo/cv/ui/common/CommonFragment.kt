package com.neocaptainnemo.cv.ui.common

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.daggerInject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_common.*
import javax.inject.Inject


class CommonFragment : Fragment() {

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

        commonList.layoutManager = LinearLayoutManager(context)
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
