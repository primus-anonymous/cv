package com.neocaptainnemo.cv.ui.common

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.ui.ArrayAdapter
import kotlinx.android.synthetic.main.item_section.view.*
import javax.inject.Inject

class CommonAdapter @Inject constructor() : ArrayAdapter<CommonSection, CommonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]

        holder.root.commonTitle.text = item.title

        holder.root.commonDescription.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(item.description)
                }

    }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root)
}
