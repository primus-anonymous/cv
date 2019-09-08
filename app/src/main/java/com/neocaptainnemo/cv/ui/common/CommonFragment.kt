package com.neocaptainnemo.cv.ui.common

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.BaseFragment
import com.neocaptainnemo.cv.ui.adapter.DiffAdapter
import com.neocaptainnemo.cv.visibleIf
import kotlinx.android.synthetic.main.fragment_common.*
import org.koin.android.viewmodel.ext.android.viewModel


class CommonFragment : BaseFragment(R.layout.fragment_common) {

    private val adapter: DiffAdapter = DiffAdapter(
            listOf(CommonBinder())
    )

    private val vModel: CommonViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonList.layoutManager = LinearLayoutManager(context)
        commonList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        autoDispose {
            vModel.progress.subscribe {
                commonsProgress.visibleIf { it }
            }
            vModel.empty.subscribe {
                commonsEmpty.visibleIf { it }
            }
            vModel.commons.subscribe {
                adapter.swapData(it)
            }
        }
    }
}
