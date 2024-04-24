package com.example.androidapp2024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainHomeActivity2 : AppCompatActivity() {
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home2)

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
//        navController?.let {
//            NavigationUI.setupActionBarWithNavController(this, it)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
       // menuInflater.inflate(R.menu.botoom_menu, menu)
        return true
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
}

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