package com.neocaptainnemo.cv.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.adapter.DiffAdapter
import com.neocaptainnemo.cv.visibleIf
import kotlinx.android.synthetic.main.fragment_common.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CommonFragment : Fragment(R.layout.fragment_common) {

    private val adapter: DiffAdapter = DiffAdapter(
            listOf(CommonBinder())
    )

    private val vModel: CommonViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CommonFragment.adapter
        }

        vModel.commons().observe(viewLifecycleOwner) {
            adapter.swapData(it)
        }

        vModel.empty.observe(viewLifecycleOwner) {
            commonsEmpty.visibleIf { it }
        }

        vModel.progress.observe(viewLifecycleOwner) {
            commonsProgress.visibleIf { it }
        }
    }
}
