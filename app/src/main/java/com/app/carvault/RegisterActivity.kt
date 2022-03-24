package com.app.carvault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        // Find the login button & launch new activity
        val register_button = findViewById<Button>(R.id.register)
        register_button.setOnClickListener {
            Toast.makeText(this.applicationContext, "Register!", Toast.LENGTH_SHORT).show()
        }
    }
}