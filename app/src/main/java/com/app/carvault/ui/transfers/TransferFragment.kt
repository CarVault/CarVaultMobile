package com.app.carvault.ui.transfers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.carvault.R


class TransferFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val v = inflater.inflate(R.layout.fragment_transfer, container, false)

        // Listener on details button
        val detailsButton: View = v.findViewById(R.id.details)
        detailsButton.setOnClickListener {
           Toast.makeText(this.context, "See car details!", Toast.LENGTH_SHORT).show()
        }

        // Listener on transfer button
        val transferButton: View = v.findViewById(R.id.transferButton)
        transferButton.setOnClickListener {
            Toast.makeText(this.context, "Transfer vehicle!", Toast.LENGTH_SHORT).show()
        }
        return v
    }

}