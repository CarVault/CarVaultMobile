package com.app.carvault.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TransactionListAdapter (private val onClick: (Transaction) -> Unit) :
    ListAdapter<Transaction, TransactionListAdapter.TransactionViewHolder>(TransactionDiffCallback) {

        class TransactionViewHolder(view: View, val onClick: (Transaction) -> Unit, val context: Context) : RecyclerView.ViewHolder(view) {
            private val date: TextView = view.findViewById(R.id.trans_date)
            private val hash: TextView = view.findViewById(R.id.hash)

            fun bind(t: Transaction) {
                hash.text = t.hash
                itemView.setOnClickListener {
                    onClick(t)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_item, parent, false)
            return TransactionViewHolder(layoutInflater, onClick, parent.context)
        }

        override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
            runBlocking {
                launch {
                    val item = getItem(position)
                    holder.bind(item)
                }
            }

        }
    }

object TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.hash == newItem.hash
    }
}