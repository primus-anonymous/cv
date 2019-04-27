package com.neocaptainnemo.cv.ui.contacts

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.neocaptainnemo.cv.R
import kotlinx.android.synthetic.main.item_contact.view.*
import kotlinx.android.synthetic.main.item_contacts_header.view.*
import java.util.*
import javax.inject.Inject

typealias ContactClicked = (contact: ContactSection) -> Unit


class ContactsAdapter @Inject constructor() : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val typeHeader = 0
    private val typeItem = 1

    var itemClicked: ContactClicked? = null

    var contactsHeader: ContactsHeader = ContactsHeader("", "", "")

    private var data: MutableList<ContactSection> = ArrayList()

    override fun getItemCount(): Int = data.size + 1

    fun clear() = data.clear()

    fun add(dataToAdd: Collection<ContactSection>) = data.addAll(dataToAdd)

    override fun getItemViewType(position: Int): Int = if (position == 0) typeHeader else typeItem


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        if (viewType == typeItem) {
            return ContactsAdapter.ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false), this)
        }

        return ContactsAdapter.ContactsHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contacts_header, parent, false))
    }


    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

        if (holder is ContactsAdapter.ContactsViewHolder) {
            val item = data[position - 1]

            holder.root.contactTitle.setText(item.title)
            holder.root.contactDescription.setText(item.subTitle)
            holder.root.contactIcon.setImageResource(item.img)
        }

        if (holder is ContactsAdapter.ContactsHeaderViewHolder) {

            val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

            Glide.with(holder.root.profilePicture)
                    .load(contactsHeader.image)
                    .apply(requestOption)
                    .into(holder.root.profilePicture)

            holder.root.profileName.text = contactsHeader.name
            holder.root.profileProfession.text = contactsHeader.profession
        }

    }


    class ContactsViewHolder(val root: View, adapter: ContactsAdapter) : androidx.recyclerview.widget.RecyclerView.ViewHolder(root) {
        init {
            root.setOnClickListener { adapter.itemClicked?.invoke(adapter.data[adapterPosition - 1]) }
        }
    }

    class ContactsHeaderViewHolder(val root: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(root)

}