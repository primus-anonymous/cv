package com.neocaptainnemo.cv.ui.common

import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.model.CommonSection
import com.neocaptainnemo.cv.spanned
import com.neocaptainnemo.cv.ui.adapter.AdapterBinder
import com.neocaptainnemo.cv.ui.adapter.AdapterItem
import com.neocaptainnemo.cv.ui.adapter.DiffViewHolder
import kotlinx.android.synthetic.main.item_section.view.*

class CommonBinder : AdapterBinder<CommonSection>() {

    override fun bindItem(item: CommonSection, holder: DiffViewHolder) {
        with(holder.itemView) {
            commonTitle.text = item.title
            commonDescription.text = item.description.spanned
        }
    }

    override val layout: Int = R.layout.item_section

    override fun shouldIProcess(item: AdapterItem): Boolean = item is CommonSection
}
