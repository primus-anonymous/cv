package com.neocaptainnemo.cv.ui

import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class ArrayAdapter<T, V : androidx.recyclerview.widget.RecyclerView.ViewHolder>() : androidx.recyclerview.widget.RecyclerView.Adapter<V>() {

    protected var data: MutableList<T> = ArrayList()

    override fun getItemCount(): Int = data.size

    fun clear() = data.clear()

    fun add(dataToAdd: Collection<T>) = data.addAll(dataToAdd)

    fun add(item: T) = data.add(item)
}
