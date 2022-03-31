package com.app.carvault.ui.profile.editProfile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.app.carvault.R
import com.app.carvault.car.carDetail.CarDetailViewModelFactory
import com.google.android.material.textfield.TextInputEditText

const val PROFILE_NAME = "name"
const val PROFILE_EMAIL = "email"
const val PROFILE_PHONE = "phone"

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editName: TextInputEditText
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPhone: TextInputEditText

    private val editProfileViewModel by viewModels<EditProfileViewModel> {
        CarDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        findViewById<Button>(R.id.doneButton).setOnClickListener {
            editProfile()
        }
        editName = findViewById(R.id.editNameInput)
        editEmail = findViewById(R.id.editEmailInput)
        editPhone = findViewById(R.id.editPhoneInput)
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
}