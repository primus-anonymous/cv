package com.neocaptainnemo.cv.ui.contacts

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.neocaptainnemo.cv.R
import com.neocaptainnemo.cv.ui.adapter.AdapterBinder
import com.neocaptainnemo.cv.ui.adapter.AdapterItem
import com.neocaptainnemo.cv.ui.adapter.DiffViewHolder
import kotlinx.android.synthetic.main.item_contact.view.*
import kotlinx.android.synthetic.main.item_contacts_header.view.*

typealias ContactClicked = (contact: ContactSection) -> Unit


class ContactsBinder : AdapterBinder<ContactSection>() {

    var itemClicked: ContactClicked? = null


    override fun bindItem(item: ContactSection, holder: DiffViewHolder) {
        with(holder.itemView) {
            contactTitle.setText(item.title)
            contactDescription.setText(item.subTitle)
            contactIcon.setImageResource(item.img)

            setOnClickListener { itemClicked?.invoke(item) }

        }
    }

    override val layout: Int = R.layout.item_contact

    override fun shouldIProcess(item: AdapterItem): Boolean = item is ContactSection
}


class ContactsHeaderBinder : AdapterBinder<ContactsHeader>() {

    override fun bindItem(item: ContactsHeader, holder: DiffViewHolder) {
        val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

        with(holder.itemView) {
            Glide.with(profilePicture)
                    .load(item.image)
                    .apply(requestOption)
                    .into(profilePicture)

            profileName.text = item.name
            profileProfession.text = item.profession

        }
    }

    override val layout: Int = R.layout.item_contacts_header

    override fun shouldIProcess(item: AdapterItem): Boolean = item is ContactsHeader

}
