package com.neocaptainnemo.cv.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

abstract class AdapterBinder<in T : AdapterItem> {

    abstract fun bindItem(item: T, holder: DiffViewHolder)

    fun onBindViewHolder(item: AdapterItem, holder: DiffViewHolder) = bindItem(item as T, holder)

    abstract fun layout(): Int

    fun createHolder(container: ViewGroup) = DiffViewHolder(LayoutInflater.from(container.context).inflate(layout(), container, false))

    abstract fun shouldIProcess(item: AdapterItem): Boolean
}