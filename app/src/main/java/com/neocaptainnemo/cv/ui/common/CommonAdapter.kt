package com.neocaptainnemo.cv.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.fromHtml
import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.ui.ArrayAdapter
import kotlinx.android.synthetic.main.item_section.view.*

class CommonAdapter : ArrayAdapter<CommonSection, CommonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]

        with(holder.root) {
            commonTitle.text = item.title
            commonDescription.text = item.description.fromHtml()
        }
    }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root)
}
