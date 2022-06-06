package com.app.carvault.documents

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import java.time.format.DateTimeFormatter

class DocumentListAdapter (private val onClick: (Document) -> Unit) :
    ListAdapter<Document, DocumentListAdapter.DocumentViewHolder>(DocumentDiffCallback) {

    class DocumentViewHolder(view: View, val onClick: (Document) -> Unit, val context: Context) : RecyclerView.ViewHolder(view) {
        private val docName: TextView = view.findViewById(R.id.docName)
        private val date: TextView = view.findViewById(R.id.docDate)
        private val typeIcon: ImageView = view.findViewById(R.id.docType)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(doc: Document) {
            doc.date?.let {
                date.text = doc.date!!.format(DateTimeFormatter.ofPattern("dd-mm-yy"))
            }
            docName.text = doc.name
            setDocTypeIcon(doc.type)
            itemView.setOnClickListener {
                onClick(doc)
            }
        }
        private fun setDocTypeIcon(type: String?){
            when (type){
                "PDF" -> typeIcon.setImageResource(R.drawable.ic_pdf_svgrepo_com__1_)
                "PNG" -> typeIcon.setImageResource(R.drawable.ic_png_svgrepo_com)
                "JPEG","JPG"-> typeIcon.setImageResource(R.drawable.ic_jpg_svgrepo_com)
                else -> typeIcon.setImageResource(R.drawable.ic_file_svgrepo_com)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.document_item, parent, false)
        return DocumentViewHolder(layoutInflater, onClick, parent.context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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