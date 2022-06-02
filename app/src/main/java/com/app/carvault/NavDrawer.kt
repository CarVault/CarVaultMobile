package com.app.carvault

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.carvault.databinding.ActivityNavDrawerBinding
import com.app.carvault.graphql.GraphqlClient
import com.app.carvault.ui.notifications.NotificationsActivity
import com.app.carvault.ui.settings.SettingsActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class NavDrawer : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarNavDrawer.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_search, R.id.nav_transfer
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupHeader(navView)
    }

    private fun setupHeader(navView: NavigationView){
        val user = GraphqlClient.getInstance().getCurrentUser()
        val headerView: View = navView.getHeaderView(0)
        val userImg = headerView.findViewById<ImageView>(R.id.userImage)
        val username = headerView.findViewById<TextView>(R.id.userName)
        val userEmail = headerView.findViewById<TextView>(R.id.userEmail)

        user?.let {
            username.text = getString(R.string.profileName, user.firstname ,user.surname )
            userEmail.text = user.email
            val bitMapImage = Util.bitmapImageFromString64(user.profilePicture, true)
            if (bitMapImage != null){
                userImg.setImageBitmap(bitMapImage)
            } else {
                userImg.setImageResource(R.drawable.ic_baseline_person_24)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
            val intent = Intent(this.applicationContext, SettingsActivity()::class.java)
            startActivity(intent)
            true
        }
        R.id.action_logout -> {
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
            true
        }
        R.id.action_notifications -> {
            val intent = Intent(this.applicationContext, NotificationsActivity()::class.java)
            startActivity(intent)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}