package com.app.carvault.ui.profile.editProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.carvault.R
import com.app.carvault.car.addCar.CAR_NAME
import com.app.carvault.car.addCar.CAR_VIN
import com.app.carvault.graphql.GraphqlClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

const val PROFILE_NAME = "name"
const val PROFILE_EMAIL = "email"
const val PROFILE_PHONE = "phone"
const val PICK_IMG_FILE = 1

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editFirstname: TextInputEditText
    private lateinit var editSurname: TextInputEditText
    private lateinit var editPhone: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        // Change img
        findViewById<Button>(R.id.updateImgButton).setOnClickListener {
            pickPhotoFromGallery()
        }

        findViewById<Button>(R.id.doneButton).setOnClickListener {
            editProfile()
        }
        editFirstname = findViewById(R.id.firstname)
        editSurname = findViewById(R.id.surname)
        editPhone = findViewById(R.id.phone)

        editFirstname.setText(GraphqlClient.getInstance().getCurrentUser()!!.firstname)
        editSurname.setText(GraphqlClient.getInstance().getCurrentUser()!!.surname)
        editPhone.setText(GraphqlClient.getInstance().getCurrentUser()!!.phone)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun editProfile(){
        lifecycleScope.launch {
            GraphqlClient.getInstance().updateUser(
                userId = GraphqlClient.getInstance().getCurrentUser()!!.id.toString(),
                firstname = editFirstname.text.toString(),
                surname = editSurname.text.toString(),
                phone = editPhone.text.toString(),
                profilePicture = GraphqlClient.getInstance().getCurrentUser()!!.profilePicture,
            )
        }
        GraphqlClient.getInstance().getCurrentUser()!!.firstname = editFirstname.text.toString()
        GraphqlClient.getInstance().getCurrentUser()!!.surname = editSurname.text.toString()
        GraphqlClient.getInstance().getCurrentUser()!!.phone = editPhone.text.toString()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMG_FILE )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                1  -> intentData?.let { data ->

                }
            }
        }
    }
}