package com.app.carvault.documents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R

class DocumentListAdapter (private val onClick: (Document) -> Unit) :
    ListAdapter<Document, DocumentListAdapter.DocumentViewHolder>(DocumentDiffCallback) {

    class DocumentViewHolder(view: View, val onClick: (Document) -> Unit, val context: Context) : RecyclerView.ViewHolder(view) {
        private val docName: TextView = view.findViewById(R.id.docName)
        private val date: TextView = view.findViewById(R.id.trans_date)

        fun bind(doc: Document) {
            date.text = doc.date
            docName.text = doc.name
            itemView.setOnClickListener {
                onClick(doc)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return DocumentViewHolder(layoutInflater, onClick, parent.context)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

object DocumentDiffCallback : DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem.id == newItem.id
    }
}