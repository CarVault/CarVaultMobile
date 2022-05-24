package com.app.carvault.ui.profile.editProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.app.carvault.R
import com.google.android.material.textfield.TextInputEditText

const val PROFILE_NAME = "name"
const val PROFILE_EMAIL = "email"
const val PROFILE_PHONE = "phone"
const val PICK_IMG_FILE = 1

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editName: TextInputEditText
    private lateinit var editEmail: TextInputEditText
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
        editName = findViewById(R.id.editNameInput)
        editEmail = findViewById(R.id.editEmailInput)
        editPhone = findViewById(R.id.editPhoneInput)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun editProfile(){
        val resultIntent = Intent()

        if ( editEmail.text.isNullOrEmpty()
            || editEmail.text.isNullOrEmpty()
            || editPhone.text.isNullOrEmpty() ) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val phone = editEmail.text.toString()
            resultIntent.putExtra(PROFILE_NAME, name)
            resultIntent.putExtra(PROFILE_EMAIL, email)
            resultIntent.putExtra(PROFILE_PHONE, phone)
            setResult(Activity.RESULT_OK, resultIntent)
        }
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
}