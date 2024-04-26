package com.example.androidapp2024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
class MainHomeActivity2 : AppCompatActivity() {
    private var navController: NavController? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home2)
      //  supportActionBar?.title = "Hello"
        // Initialize Firebase components

        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.navHostMain) as? NavHostFragment
        navController = navHostFragment?.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    // Handle Home item click
                    navController?.navigate(R.id.action_global_usersFragment)
                    true
                }

                R.id.done -> {
                    // Handle Selected item click
                    true
                }

                R.id.add -> {
                    // Handle AddPost item click
                    navController?.navigate(R.id.action_global_addUserFragment4)
                    true
                }

                R.id.profile -> {
                    // Handle Profile item click
                    true
                }

                else -> false
            }
        }
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Set greeting in ActionBar
      //  setActionBarGreeting()

    }
//    private fun setActionBarGreeting() {
//        // Get the currently signed-in user
//        val currentUser = auth.currentUser
//        currentUser?.let { user ->
//            firestore.collection("Users").document(user.uid)
//                .get()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        val username = document.getString("name") ?: ""
//                        // Update ActionBar title with personalized greeting
//                        supportActionBar?.title = "Hello, $username"
//                    } else {
//                        // If user data doesn't exist, set a default greeting
//                        supportActionBar?.title = "Hello"
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.d("MainHomeActivity2", "get failed with ", exception)
//                    // Handle failure to retrieve user data
//                    supportActionBar?.title = "Hello"
//                }
//        } ?: run {
//            // If no user is signed in, set a default greeting
//            supportActionBar?.title = "Hello"
//        }
//    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        // menuInflater.inflate(R.menu.botoom_menu, menu)
        return true
    }
}
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                navController?.popBackStack()
//                true
//            }
//
//            R.id.add -> {
//                navController?.navigate(R.id.action_global_addUserFragment4)
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//}

//        if(item.itemId == android.R.id.home){
//            navController?.popBackStack()
//            return true
//        } else if (item.itemId == R.id.add){
//            // when clicking on home go to global add
//            navController?.navigate(R.id.action_global_addUserFragment4)
//            return true
//        }
//        else {
//            return super.onOptionsItemSelected(item)
//        }
//    }
//}