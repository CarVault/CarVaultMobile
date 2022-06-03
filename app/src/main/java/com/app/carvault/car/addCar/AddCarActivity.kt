package com.app.carvault.car.addCar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.MenuItem
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
        setContentView(R.layout.activity_add_car_new)

        /*findViewById<Button>(R.id.addDocButton).setOnClickListener {
            openFile();
        }*/

        /*findViewById<Button>(R.id.doneButton).setOnClickListener {
            addCar()
        }
        addCarName = findViewById(R.id.addCarBrand)
        addCarVIN = findViewById(R.id.addCarVin)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)*/
    }

    /*private fun addCar() {
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
    }*/

    /*private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }*/

}