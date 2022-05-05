package com.app.carvault.car.addCar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Button
import com.app.carvault.R
import com.google.android.material.textfield.TextInputEditText

const val CAR_NAME = "name"
const val CAR_VIN = "VIN"
const val PICK_PDF_FILE = 2

class AddCarActivity : AppCompatActivity() {
    private lateinit var addCarName: TextInputEditText
    private lateinit var addCarVIN: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)

        findViewById<Button>(R.id.addDocButton).setOnClickListener {
            openFile();
        }

        findViewById<Button>(R.id.doneButton).setOnClickListener {
            addCar()
        }
        addCarName = findViewById(R.id.addCarName)
        addCarVIN = findViewById(R.id.addCarVIN)
    }

    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addCar() {
        val resultIntent = Intent()

        if (addCarName.text.isNullOrEmpty() || addCarVIN.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addCarName.text.toString()
            val description = addCarVIN.text.toString()
            resultIntent.putExtra(CAR_NAME, name)
            resultIntent.putExtra(CAR_VIN, description)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"

        }

        startActivityForResult(intent, PICK_PDF_FILE)
    }

}