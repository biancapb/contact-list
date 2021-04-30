package com.example.listadecontato

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(var listener: ClickItemContactListener) : RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>() {

    private val list: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactAdapterViewHolder(view, list, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(list: List<Contact>) {
        this.list.clear()
        this.list.addAll(list)
        //notifica o adapter de que a lista foi modificada
        notifyDataSetChanged()
    }

    class ContactAdapterViewHolder(itemView: View, var list: List<Contact>, var listener: ClickItemContactListener) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.text_name)
        private val tvPhone: TextView = itemView.findViewById(R.id.text_phone)

        init {
            itemView.setOnClickListener {
                listener.clickItemContact(list[adapterPosition])
            }
        }

        fun bind(contact: Contact) {
            tvName.text = contact.name
            tvPhone.text = contact.phone

        }
    }

}