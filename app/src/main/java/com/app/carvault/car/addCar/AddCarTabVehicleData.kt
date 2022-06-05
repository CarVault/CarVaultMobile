package com.app.carvault.car.addCar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.graphql.GraphqlClient
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import com.app.carvault.car.addCar.*


class AddCarTabVehicleData : Fragment() {

    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var vin: EditText
    private lateinit var brand: EditText
    private lateinit var model: EditText
    private lateinit var description: EditText
    private lateinit var kilometers: EditText
    private lateinit var horsepower: EditText
    private lateinit var year: EditText
    private lateinit var address: EditText
    private lateinit var manufacturer: EditText
    private lateinit var origin: EditText
    private lateinit var fuel: EditText
    private lateinit var color: EditText

    private lateinit var vinLayout: TextView
    private lateinit var modelLayout: TextView
    private lateinit var brandLayout: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_add_car_tab_vehicle_data, container, false)
        v.findViewById<Button>(R.id.addDocButton).setOnClickListener {
            openFile();
        }

        /*v.findViewById<Button>(R.id.addDocButton3).setOnClickListener{
            dispatchTakePictureIntent();
        }
        v.findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
            dispatchTakePictureIntent();
        }*/
        /*v.findViewById<Button>(R.id.addVehicleButton).setOnClickListener {
            addCar()
        }*/

        brand = v.findViewById(R.id.brand)
        vin = v.findViewById(R.id.vin)
        model = v.findViewById(R.id.model)
        description = v.findViewById(R.id.description)
        kilometers = v.findViewById(R.id.kms)
        horsepower = v.findViewById(R.id.horsepower)
        year = v.findViewById(R.id.year)
        address = v.findViewById(R.id.address)
        manufacturer = v.findViewById(R.id.manufacturer)
        origin = v.findViewById(R.id.origin)
        fuel = v.findViewById(R.id.fuel)
        color = v.findViewById(R.id.color)

        vinLayout = v.findViewById(R.id.vinLayout)
        modelLayout = v.findViewById(R.id.modelLayout)
        brandLayout = v.findViewById(R.id.brandLayout)
        return v
    }

    /*private fun addCar() {
        if (!checkMandatoryFields()){
            Toast.makeText(applicationContext, getString(R.string.toast_mandatory), Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            GraphqlClient.getInstance().addCar(
                userId = GraphqlClient.getInstance().getCurrentUser()?.id.toString(),
                vin = vin.text.toString(),
                model = model.text.toString(),
                brand = brand.text.toString(),
                description = description.text.toString(),
                kilometers = if (kilometers.text.toString().isNotBlank()) {
                    kilometers.text.toString().toInt() } else {null},
                horsepower = if (horsepower.text.toString().isNotBlank()) {
                    horsepower.text.toString().toInt() } else {null},
                year = if (year.text.toString().isNotBlank()) {
                    year.text.toString().toInt() } else {null},
                address = address.text.toString(),
                manufacturer = manufacturer.text.toString(),
                origin = origin.text.toString(),
                fuel = fuel.text.toString(),
                color = color.text.toString()
            )
        }
        finish()
    }*/

    private fun checkMandatoryFields(): Boolean {
        var check = true
        if (vin.text.isNullOrBlank() || vin.text.isNullOrEmpty()){
            vinLayout.hint = getString(R.string.vin_hint_asterisk)
            vinLayout.setHintTextColor(0xFF0000)
            check =  false
        } else {
            vinLayout.hint = getString(R.string.vin_hint)
        }
        if (model.text.isNullOrBlank() || model.text.isNullOrEmpty()){
            modelLayout.hint = getString(R.string.model_hint_asterisk)
            modelLayout.setHintTextColor(0xFF0000)
            check = false
        } else {
            modelLayout.hint = getString(R.string.model_hint)
        }
        if (brand.text.isNullOrBlank() || brand.text.isNullOrEmpty()){
            brandLayout.hint = getString(R.string.brand_hint_asterisk)
            brandLayout.setHintTextColor(0xFF0000)
            check = false
        } else {
            brandLayout.hint = getString(R.string.brand_hint)
        }
        return check
    }

    private fun TextInputLayout.makeHintRed() {
        hint = buildSpannedString {
            color(Color.RED) { append(hint) } // Mind the space prefix.
            color(Color.RED) { append(" ") }
        }
    }


    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }






   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageView : ImageView = findViewById(R.id.image)
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }*/

}