package com.app.carvault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.api.Optional
import com.app.carvault.graphql.GraphqlClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var firstnameInput: EditText
    private lateinit var surnameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var usernameInput: EditText
    private lateinit var passInput: EditText
    private lateinit var confirmInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        // Find the login button & launch new activity
        firstnameInput = findViewById(R.id.firstname)
        surnameInput = findViewById(R.id.surname)
        emailInput = findViewById(R.id.emailInput)
        usernameInput = findViewById(R.id.usernameInput)
        passInput = findViewById(R.id.passwordInput)
        confirmInput = findViewById(R.id.confirmPassInput)
        phoneInput = findViewById(R.id.phoneInput)
        registerButton = findViewById(R.id.register)

        registerButton.setOnClickListener {
            if (firstnameInput.text.isEmpty() || firstnameInput.text.isNullOrBlank()){
                Toast.makeText(this, "Please introduce a valid firstname.", Toast.LENGTH_SHORT).show()
            } else if (surnameInput.text.isEmpty() || surnameInput.text.isNullOrBlank()){
                Toast.makeText(this, "Please introduce a valid surname.", Toast.LENGTH_SHORT).show()
            }else if (emailInput.text.isEmpty() || emailInput.text.isNullOrBlank()) {
                Toast.makeText(this, "Please introduce a valid email account.", Toast.LENGTH_SHORT).show()
            }else if (phoneInput.text.isEmpty() || phoneInput.text.isNullOrBlank()){
                Toast.makeText(this, "Please introduce a valid phone number.", Toast.LENGTH_SHORT).show()
            }else if (usernameInput.text.isEmpty() || usernameInput.text.isNullOrBlank()){
                Toast.makeText(this, "Please introduce a valid username.", Toast.LENGTH_SHORT).show()
            }else if (passInput.text.isEmpty() || passInput.text.isNullOrBlank()){
                Toast.makeText(this, "Please introduce a password.", Toast.LENGTH_SHORT).show()
            }else if (confirmInput.text.isEmpty() || confirmInput.text.isNullOrBlank()){
                Toast.makeText(this, "Please confirm your password.", Toast.LENGTH_SHORT).show()
            }else if (passInput.text.toString() != confirmInput.text.toString()) {
                Toast.makeText(this, "Please ensure both passwords match.", Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailInput.text.toString(), passInput.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            // End of the registration -> Create user on our backend
                            lifecycleScope.launch {
                                val newUserId = withContext(Dispatchers.IO) {
                                    GraphqlClient.getInstance().addUser(
                                        username = usernameInput.text.toString(),
                                        email = emailInput.text.toString(),
                                        firstname = firstnameInput.text.toString(),
                                        surname = surnameInput.text.toString(),
                                        phone = phoneInput.text.toString(),
                                        profilePicture = emailInput.text.toString(),
                                    )
                                }
                                onBackPressed()
                            }
                        }else{
                            showAlert()
                        }
                    }
            }

        }
    }


    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("An error has occurred during user authentication")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}