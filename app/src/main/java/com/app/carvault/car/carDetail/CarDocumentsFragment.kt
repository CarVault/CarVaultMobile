package com.app.carvault.car.carDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.carvault.R
import com.app.carvault.car.Car
import com.app.carvault.documents.Document
import com.app.carvault.documents.DocumentListAdapter
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.transaction.TransactionListAdapter

class CarDocumentsFragment(val car: Car?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_car_documents, container, false)

        val adapter = DocumentListAdapter { doc -> onClickOpenDoc(doc)}
        val recyclerView = v.findViewById<RecyclerView>(R.id.document_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        car?.let {
            adapter.submitList(car.documents)
        }

        return v
    }

    private fun onClickOpenDoc(doc: Document){
        Toast.makeText(this.requireContext(), doc.name, Toast.LENGTH_SHORT).show()
    }

}