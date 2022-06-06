package com.app.carvault.car.editCar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.app.carvault.R

const val PICK_PDF_FILE = 2

class EditCarTabDocumentationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val editCar = EditCar()
        val v = inflater.inflate(R.layout.fragment_edit_car_tab_documentation, container, false)
        v.findViewById<Button>(R.id.editCarAddDocButton).setOnClickListener {
            openFile();
        }
        v.findViewById<ImageButton>(R.id.editCarAddDocButton).setOnClickListener {
            editCar.dispatchTakePictureIntent();
        }
        return v;
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }

}