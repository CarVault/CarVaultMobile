package com.app.carvault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        // Find the login button & launch new activity
        val login_button = findViewById<Button>(R.id.login_button)
        login_button.setOnClickListener {
            val intent = Intent(this, NavDrawer::class.java)
            startActivity(intent)
        }
    }
}