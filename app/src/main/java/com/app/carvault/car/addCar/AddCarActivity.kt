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

const val CAR_BRAND = "brand"
const val CAR_MODEL = "model"
const val CAR_VIN = "VIN"
const val CAR_MANUFACTURER = "manufacturer"
const val CAR_MANUFACTURER_YEAR = "manufacturer year"
const val CAR_ORIGIN = "origin"
const val CAR_ADDRESS = "address"
const val CAR_KMS = "kms"
const val CAR_FUEL = "fuel"
const val CAR_COLOR = "color"
const val CAR_DESCRIPTION = "description"
const val PICK_PDF_FILE = 2

class AddCarActivity : AppCompatActivity() {
    private lateinit var addCarBrand: TextInputEditText
    private lateinit var addCarModel: TextInputEditText
    private lateinit var addCarVIN: TextInputEditText
    private lateinit var addCarManufacturer: TextInputEditText
    private lateinit var addCarManufacturerYear: TextInputEditText
    private lateinit var addCarOrigin: TextInputEditText
    private lateinit var addCarAddress: TextInputEditText
    private lateinit var addCarKMS: TextInputEditText
    private lateinit var addCarFuel: TextInputEditText
    private lateinit var addCarColor: TextInputEditText
    private lateinit var addCarDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car_new)

        /*findViewById<Button>(R.id.addDocButton).setOnClickListener {
            openFile();
        }*/

        findViewById<Button>(R.id.addVehicleButton).setOnClickListener {
            addCar()
        }
        addCarBrand = findViewById(R.id.addCarBrand)
        addCarModel = findViewById(R.id.addCarModel)
        addCarVIN = findViewById(R.id.addCarVin)
        addCarManufacturer = findViewById(R.id.addCarManufacturer)
        addCarManufacturerYear = findViewById(R.id.addCarManufacturedYear)
        addCarOrigin = findViewById(R.id.addCarOrigin)
        addCarAddress = findViewById(R.id.addCarAddress)
        addCarKMS = findViewById(R.id.addCarKMS)
        addCarFuel = findViewById(R.id.addCarFuel)
        addCarColor = findViewById(R.id.addCarColor)
        addCarDescription = findViewById(R.id.addCarDescription)
    }

    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addCar() {
        val resultIntent = Intent()

        if (addCarBrand.text.isNullOrEmpty() || addCarModel.text.isNullOrEmpty() || addCarVIN.text.isNullOrEmpty()
            || addCarManufacturer.text.isNullOrEmpty() || addCarManufacturerYear.text.isNullOrEmpty() || addCarOrigin.text.isNullOrEmpty()
            || addCarAddress.text.isNullOrEmpty() || addCarKMS.text.isNullOrEmpty() || addCarFuel.text.isNullOrEmpty()
            || addCarColor.text.isNullOrEmpty() || addCarDescription.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val brand = addCarBrand.text.toString()
            val model = addCarModel.text.toString()
            val vin = addCarVIN.text.toString()
            val manufacturer = addCarManufacturer.text.toString()
            val manufacturer_year = addCarManufacturerYear.text.toString()
            val origin = addCarOrigin.text.toString()
            val address = addCarAddress.text.toString()
            val KMS = addCarKMS.text.toString()
            val fuel = addCarFuel.text.toString()
            val color = addCarColor.text.toString()
            val description= addCarDescription.text.toString()
            resultIntent.putExtra(CAR_BRAND, brand)
            resultIntent.putExtra(CAR_MODEL, model)
            resultIntent.putExtra(CAR_VIN, vin)
            resultIntent.putExtra(CAR_MANUFACTURER, manufacturer)
            resultIntent.putExtra(CAR_MANUFACTURER_YEAR, manufacturer_year)
            resultIntent.putExtra(CAR_ORIGIN, origin)
            resultIntent.putExtra(CAR_ADDRESS, address)
            resultIntent.putExtra(CAR_KMS, KMS)
            resultIntent.putExtra(CAR_FUEL, fuel)
            resultIntent.putExtra(CAR_COLOR, color)
            resultIntent.putExtra(CAR_DESCRIPTION, description)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    /*private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"

        }

        startActivityForResult(intent, PICK_PDF_FILE)
    }*/

}