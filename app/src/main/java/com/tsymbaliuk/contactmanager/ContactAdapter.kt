package com.tsymbaliuk.contactmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.card.MaterialCardView

class ContactAdapter(private val context: Context) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contactList = listOf<ContactItem>()

    private lateinit var openItemClickListener: (Int) -> Unit

    fun setOpenItemClickListener(clickListener: (Int) -> Unit) {
        this.openItemClickListener = clickListener
    }

    private lateinit var deleteItemClickListener: (Int) -> Unit

    fun setDeleteItemClickListener(clickListener: (Int) -> Unit) {
        this.deleteItemClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contactList[position]

        Glide.with(context).load(currentContact.photoId).transform(CircleCrop())
            .into(holder.photo)

        holder.firstName.text = currentContact.firstName

        holder.secondName.text = currentContact.secondName

        holder.delete.setOnClickListener {
            if (::deleteItemClickListener.isInitialized) {
                deleteItemClickListener(holder.adapterPosition)
            }
        }

        holder.card.setOnClickListener {
            if (::openItemClickListener.isInitialized) {
                openItemClickListener(holder.adapterPosition)
            }
        }
    }

    inner class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val photo = itemView.findViewById<ImageView>(R.id.photo)
        val firstName = itemView.findViewById<TextView>(R.id.first_name)
        val secondName = itemView.findViewById<TextView>(R.id.second_name)
        val delete = itemView.findViewById<ImageView>(R.id.delete)
        val card = itemView.findViewById<MaterialCardView>(R.id.card)
    }

    fun updateData(newContactList: List<ContactItem>) {
        contactList = newContactList
        notifyDataSetChanged()
    }

}

