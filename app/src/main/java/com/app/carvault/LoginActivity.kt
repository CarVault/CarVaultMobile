package com.app.carvault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.app.carvault.user.UserDataSource
import javax.sql.DataSource

class LoginActivity : AppCompatActivity() {

    private lateinit var userDataSource: UserDataSource
    private lateinit var emailInput: EditText
    private lateinit var passInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        userDataSource = UserDataSource.getDataSource(this)

        // Find the login button & launch new activity
        val loginButton = findViewById<Button>(R.id.login_button)
        emailInput = findViewById(R.id.editTextEmailAddress)
        passInput = findViewById(R.id.editTextPassword)

        loginButton.setOnClickListener {
            tryLogin()
        }
        val registerButton = findViewById<Button>(R.id.register)

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun tryLogin(){
        val emailText = emailInput.text.toString()
        val passText = passInput.text.toString()

        if (userDataSource.login(emailText, passText)){
            val intent = Intent(this, NavDrawer::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Email or password wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}