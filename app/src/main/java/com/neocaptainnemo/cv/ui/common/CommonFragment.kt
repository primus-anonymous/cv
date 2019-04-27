package com.neocaptainnemo.cv.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.BaseFragment
import com.neocaptainnemo.cv.visibleIf
import kotlinx.android.synthetic.main.fragment_common.*
import org.koin.android.viewmodel.ext.android.viewModel


class CommonFragment : BaseFragment() {

    private val adapter: CommonAdapter = CommonAdapter()

    private val vModel: CommonViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonList.layoutManager = LinearLayoutManager(context)
        commonList.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_common, container, false)

    override fun onStart() {
        super.onStart()

        autoDispose {
            vModel.progress().subscribe {
                commonsProgress.visibleIf { it }
            }
        }

        autoDispose {
            vModel.empty().subscribe {
                commonsEmpty.visibleIf { it }
            }
        }

        autoDispose {
            vModel.commons().subscribe({

                adapter.clear()
                adapter.add(it)

                adapter.notifyDataSetChanged()
            }, {

            })
        }


    }

}
