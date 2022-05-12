package com.app.carvault

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.Snackbar
import java.io.InputStream

class EulaActivity : AppCompatActivity() {

    private val EULA_ACCEPTED = "ACCEPTED"
    private lateinit var eulaLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eula)

        eulaLayout = findViewById(R.id.eulaLayout)
        val confirmButton = findViewById<Button>(R.id.eula_confirm)
        confirmButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.putString("eula", "EULA_ACCEPTED")
            prefs.apply()
            startActivity(intent)
        }

        val cancelButton = findViewById<Button>(R.id.eula_cancel)
        cancelButton.setOnClickListener{
            Snackbar.make(
                findViewById(android.R.id.content),
                "You must accept the EULA to continue",
                Snackbar.LENGTH_LONG
            ).show()
        }
        checkPreferences()
        setEula()
    }

    private fun checkPreferences(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val eulaAccepted = prefs.getString("eula", null)
        if (eulaAccepted!=null){
            val intent = Intent(this, LoginActivity::class.java)
            eulaLayout.visibility = View.INVISIBLE
            startActivity(intent)
        }
    }

    private fun setEula() {
        val eulaContent = findViewById<TextView>(R.id.eula_content)
        val inputStream: InputStream = assets.open("eula.html")
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes)
        val htmlAsSpanned: Spanned =
            HtmlCompat.fromHtml(String(bytes), HtmlCompat.FROM_HTML_MODE_LEGACY)
        eulaContent.text = htmlAsSpanned
    }
}