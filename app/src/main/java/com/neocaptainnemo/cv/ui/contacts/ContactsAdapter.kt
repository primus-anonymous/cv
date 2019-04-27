package com.neocaptainnemo.cv.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.neocaptainnemo.cv.R
import kotlinx.android.synthetic.main.item_contact.view.*
import kotlinx.android.synthetic.main.item_contacts_header.view.*
import java.util.*

typealias ContactClicked = (contact: ContactSection) -> Unit


class ContactsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val typeHeader = 0
    private val typeItem = 1

    var itemClicked: ContactClicked? = null

    var contactsHeader: ContactsHeader = ContactsHeader("", "", "")

    private var data: MutableList<ContactSection> = ArrayList()

    override fun getItemCount(): Int = data.size + 1

    fun clear() = data.clear()

    fun add(dataToAdd: Collection<ContactSection>) = data.addAll(dataToAdd)

    override fun getItemViewType(position: Int): Int = if (position == 0) typeHeader else typeItem


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == typeItem) {
                ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false), this)
            } else ContactsHeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contacts_header, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ContactsViewHolder) {
            val item = data[position - 1]

            with(holder.root) {
                contactTitle.setText(item.title)
                contactDescription.setText(item.subTitle)
                contactIcon.setImageResource(item.img)
            }
        }

        if (holder is ContactsHeaderViewHolder) {

            val requestOption = RequestOptions().error(R.drawable.placeholder).placeholder(R.drawable.placeholder)

            with(holder.root) {
                Glide.with(profilePicture)
                        .load(contactsHeader.image)
                        .apply(requestOption)
                        .into(profilePicture)

                profileName.text = contactsHeader.name
                profileProfession.text = contactsHeader.profession
            }
        }

    }


    class ContactsViewHolder(val root: View, adapter: ContactsAdapter) : RecyclerView.ViewHolder(root) {
        init {
            root.setOnClickListener { adapter.itemClicked?.invoke(adapter.data[adapterPosition - 1]) }
        }
    }

    class ContactsHeaderViewHolder(val root: View) : RecyclerView.ViewHolder(root)

}