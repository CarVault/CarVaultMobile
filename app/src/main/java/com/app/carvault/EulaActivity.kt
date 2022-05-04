package com.app.carvault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.Snackbar
import java.io.InputStream

class EulaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eula)
        setEula()



        val confirmButton = findViewById<Button>(R.id.eula_confirm)
        confirmButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
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

    }

    private fun setEula() {
        val eulaContent = findViewById<TextView>(R.id.eula_content)
        //val eula = intent.extras!!.getInt("eula")
        //val inputStream: InputStream = resources.openRawResource(eula)
        val inputStream: InputStream = assets.open("eula.html")
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes)
        val htmlAsSpanned: Spanned =
            HtmlCompat.fromHtml(String(bytes), HtmlCompat.FROM_HTML_MODE_LEGACY)
        eulaContent.text = htmlAsSpanned
    }
}