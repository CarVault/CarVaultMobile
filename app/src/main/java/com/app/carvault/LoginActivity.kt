package com.app.carvault

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
//import com.apm.graphql.UserQuery
import com.apollographql.apollo3.ApolloClient
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.user.UserDataSource
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.sql.DataSource

class LoginActivity : AppCompatActivity() {
    private lateinit var userDataSource: UserDataSource
    private lateinit var emailInput: EditText
    private lateinit var passInput: EditText
    private lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        userDataSource = UserDataSource.getDataSource(this)

        // Find the login button & launch new activity
        val loginButton = findViewById<Button>(R.id.login_button)
        emailInput = findViewById(R.id.editTextEmailAddress)
        passInput = findViewById(R.id.editTextPassword)
        spinner = findViewById(R.id.progressBar)

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
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(emailInput.text.toString(), passInput.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loadUserData()
                }else{
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Email or password wrong!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun loadUserData(){
        val intent = Intent(this, NavDrawer::class.java)
        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getUserByEmail(
                    FirebaseAuth.getInstance().currentUser?.email.toString()
                )
            }
            Thread.sleep(5000)
            user?.let {
                GraphqlClient.getInstance().setCurrentUser(user)
            }
            spinner.visibility = View.INVISIBLE
            startActivity(intent)
        }
        spinner.visibility = View.VISIBLE

    }
}