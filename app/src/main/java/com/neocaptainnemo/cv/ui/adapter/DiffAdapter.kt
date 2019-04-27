package com.neocaptainnemo.cv.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

interface AdapterItem {
    fun uniqueTag(): String
}

class DiffAdapter(private val binders: List<AdapterBinder<*>>) : RecyclerView.Adapter<DiffViewHolder>() {

    private var data = ArrayList<AdapterItem>()

    override fun getItemCount(): Int = data.size

    fun swapData(items: List<AdapterItem>) {

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    data[oldItemPosition].uniqueTag() == items[newItemPosition].uniqueTag()

            override fun getOldListSize(): Int = data.size

            override fun getNewListSize(): Int = items.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = data[oldItemPosition] == items[newItemPosition]

        })

        data.clear()
        data.addAll(items)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffViewHolder =
            binders.find { it.layout() == viewType }!!.createHolder(parent)

    override fun onBindViewHolder(holder: DiffViewHolder, position: Int) {
        val item = data[position]
        binders.find { it.shouldIProcess(item) }!!.onBindViewHolder(item, holder)
    }

    override fun getItemViewType(position: Int): Int = binders.find { it.shouldIProcess(data[position]) }!!.layout()
}

class DiffViewHolder(root: View) : RecyclerView.ViewHolder(root)