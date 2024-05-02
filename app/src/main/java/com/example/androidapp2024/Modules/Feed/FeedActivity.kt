package com.example.androidapp2024.Modules.Feed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
class FeedActivity : AppCompatActivity() {
    private var navController: NavController? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val userId = intent.getStringExtra("USER_ID")
        if (userId != null) {
            UserFirestore.getInstance().getUserData(userId, { user ->
                navigateToProfileFragment(user)

            }, { exception ->
                Log.e("FeedActivity", "Error retrieving user data: ${exception.message}")
            })
        }
        //  supportActionBar?.title = "Hello"
        // Initialize Firebase components

        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.navHostMain) as? NavHostFragment
        navController = navHostFragment?.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    navController?.navigate(R.id.action_global_postsFragment)
                    true
                }

                R.id.done -> {
                    true
                }

                R.id.add -> {
                    navController?.navigate(R.id.action_global_addPostFragment3)
                    true
                }

                R.id.profile -> {
                    navController?.navigate(R.id.action_global_profileFragment)
                    true
                }

                else -> false
            }
        }
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }
    private fun navigateToProfileFragment(userData: User) {

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        // menuInflater.inflate(R.menu.botoom_menu, menu)
        return true
    }
}