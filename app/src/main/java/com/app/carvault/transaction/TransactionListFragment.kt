package com.app.carvault.transaction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.car.CarDataSource
import com.app.carvault.car.carDetail.CarDetailActivity
import com.app.carvault.ui.profile.TRANS_ID
import com.app.carvault.user.UserDataSource

class TransactionListFragment : Fragment() {

    private lateinit var userDataSource : UserDataSource
    private lateinit var transactionDataSource: TransactionDataSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userDataSource = UserDataSource.getDataSource(this.requireContext())
        transactionDataSource = TransactionDataSource.getDataSource(this.requireContext())

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_transaction_list, container, false)
        val transAdapter = TransactionListAdapter { trans -> adapterOnClick(trans)}
        val recyclerView = v.findViewById<RecyclerView>(R.id.transaction_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = transAdapter
        transAdapter.submitList(transactionDataSource.loadTransactions(userDataSource.getCurrentUser().transactions))
        return v
    }

    private fun adapterOnClick(transaction: Transaction) {
        val intent = Intent(this.requireContext(), TransactionDetailActivity()::class.java)
        intent.putExtra(TRANS_ID, transaction.id)
        startActivity(intent)
    }
}