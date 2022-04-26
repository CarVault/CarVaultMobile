package com.app.carvault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var usernameInput: EditText
    private lateinit var passInput: EditText
    private lateinit var confirmInput: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        // Find the login button & launch new activity
        emailInput = findViewById(R.id.emailInput)
        usernameInput = findViewById(R.id.usernameInput)
        passInput = findViewById(R.id.passwordInput)
        confirmInput = findViewById(R.id.confirmPassInput)
        registerButton = findViewById(R.id.register)

        registerButton.setOnClickListener {
            if (emailInput.text.isEmpty()){
                Toast.makeText(this, "Please introduce a valid email account.", Toast.LENGTH_SHORT).show()
            }else if (usernameInput.text.isEmpty()){
                Toast.makeText(this, "Please introduce a valid username.", Toast.LENGTH_SHORT).show()
            }else if (passInput.text.isEmpty()){
                Toast.makeText(this, "Please introduce a password.", Toast.LENGTH_SHORT).show()
            }else if (confirmInput.text.isEmpty()){
                Toast.makeText(this, "Please confirm your password.", Toast.LENGTH_SHORT).show()
            }else if (passInput.text.toString() != confirmInput.text.toString()) {
                Toast.makeText(this, "Please ensure both passwords match.", Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailInput.text.toString(), passInput.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            // End of the registration
                            onBackPressed()
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