package com.app.carvault.transaction

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.Util
import com.app.carvault.car.Car

class TransactionListFragment(val car: Car?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_transaction_list, container, false)
        val transAdapter = TransactionListAdapter { trans -> adapterOnClick(trans)}
        val recyclerView = v.findViewById<RecyclerView>(R.id.transaction_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = transAdapter
        car?.let {
            transAdapter.submitList(car.transactions)
        }
        return v
    }

    private fun adapterOnClick(transaction: Transaction) {
        Util.setClipboard(this.requireContext(), "Transaction hash", transaction.hash)
        Toast.makeText(this.requireContext(), "Transaction hash copied to clipbaord!", Toast.LENGTH_SHORT).show()
    }

}