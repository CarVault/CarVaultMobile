package com.app.carvault

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
//import com.apm.graphql.UserQuery
import com.apollographql.apollo3.ApolloClient
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.user.UserDataSource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.sql.DataSource

class LoginActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 1

    private lateinit var emailInput: EditText
    private lateinit var passInput: EditText
    private lateinit var spinner: ProgressBar
    private lateinit var loginLayout: ConstraintLayout
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private lateinit var googleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Find the login button & launch new activity
        emailInput = findViewById(R.id.editTextEmailAddress)
        passInput = findViewById(R.id.editTextPassword)
        spinner = findViewById(R.id.progressBar)
        loginLayout = findViewById(R.id.loginLayout)
        registerButton = findViewById(R.id.register)
        loginButton = findViewById(R.id.login_button)
        googleButton = findViewById(R.id.googleButton)

        session()

        loginButton.setOnClickListener {
            emailAndPasswordLogin()
        }

        googleButton.setOnClickListener{
            googleLogin()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        session()
    }

    // Comprueba si existe una sesion activa
    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        if (email != null){
            loginLayout.visibility = View.INVISIBLE
            loadApp(email)
        } else{
            loginLayout.visibility = View.VISIBLE
        }

    }

    private fun emailAndPasswordLogin() {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(emailInput.text.toString(), passInput.text.toString())
            .addOnCompleteListener {
                firebaseAuthOnComplete(it, getString(R.string.auth_email_error))
            }
    }


    private fun googleLogin(){
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    private fun firebaseAuthOnComplete(task: Task<AuthResult>, messageError: String){
        if (task.isSuccessful) {
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()
            saveSession(email)
            loadApp(email)
        }else{
            showAlert(messageError)
        }
    }

    private fun saveSession(email: String){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.apply()
    }

    private fun loadApp(email: String){
        val intent = Intent(this, NavDrawer::class.java)
        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                GraphqlClient.getInstance().getUserByEmail(
                    email
                )
            }
            user?.let {
                GraphqlClient.getInstance().setCurrentUser(user)
            }
            spinner.visibility = View.INVISIBLE
            startActivity(intent)
        }
        spinner.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)

                account?.let{
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        firebaseAuthOnComplete(it, getString(R.string.auth_google_error))
                    }
                }
            } catch (e: ApiException) {
                showAlert(getString(R.string.auth_google_error))
            }

        }
    }

    private fun showAlert(message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}