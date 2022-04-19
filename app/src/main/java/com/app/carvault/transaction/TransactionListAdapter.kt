package com.app.carvault.transaction

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.user.UserDataSource

class TransactionListAdapter (private val onClick: (Transaction) -> Unit) :
    ListAdapter<Transaction, TransactionListAdapter.CarViewHolder>(TransactionDiffCallback) {
    /* Car view holder */
        class CarViewHolder(view: View, val onClick: (Transaction) -> Unit) : RecyclerView.ViewHolder(view) {
            private val date: TextView = view.findViewById(R.id.trans_date)
            private val from: TextView = view.findViewById(R.id.from)
            private val to: TextView = view.findViewById(R.id.to)
            private val carName: TextView = view.findViewById(R.id.carName)

        fun bind(transaction: Transaction) {
                date.text = transaction.date
                from.text = "From: " + transaction.from_id.toString()
                to.text = "To: " + transaction.to_id.toString()
                carName.text = transaction.car.toString()

                itemView.setOnClickListener {
                    onClick(transaction)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_item, parent, false)
            return CarViewHolder(layoutInflater, onClick)
        }

        override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }
    }

object TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }
}