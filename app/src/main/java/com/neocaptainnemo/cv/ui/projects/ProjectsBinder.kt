package com.neocaptainnemo.cv.ui.projects

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.Project
import com.neocaptainnemo.cv.ui.adapter.AdapterBinder
import com.neocaptainnemo.cv.ui.adapter.AdapterItem
import com.neocaptainnemo.cv.ui.adapter.DiffViewHolder
import kotlinx.android.synthetic.main.item_project.view.*

class ProjectsBinder : AdapterBinder<Project>() {

    var onProjectClicked: ((project: Project, transitionView: View, transitionView2: View) -> Unit)? = null

    override fun bindItem(item: Project, holder: DiffViewHolder) {
        with(holder.containerView) {

            setOnClickListener {
                onProjectClicked?.invoke(item,
                        image, itemPlatform)
            }

            infoText.text = item.name

            val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

            Glide.with(this)
                    .applyDefaultRequestOptions(requestOption)
                    .load(item.webPic)
                    .into(image)

            when(item.platform) {
                Project.PLATFORM_ANDROID -> R.drawable.ic_android
                Project.PLATFORM_IOS -> R.drawable.ic_apple
                else -> null
            }?.also {
                itemPlatform.setImageResource(it)
            }
        }
    }

    override val layout: Int = R.layout.item_project

    override fun shouldIProcess(item: AdapterItem): Boolean = item is Project
}
