package com.example.androidapp2024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.androidapp2024.Modules.User.UsersFragment

class HomeActivity : AppCompatActivity() {
    var fragmentOne: UsersFragment?= null
    var fragmentTwo: FirstFragment?= null
    var fragmentThree: FirstFragment?= null
    var fragmentFour: FirstFragment?= null

    var buttonOne: Button?= null
    var buttonTwo: Button?= null
    var buttonThree: Button?= null
    var buttonFour: Button?= null

    var inDisplayFragment: Fragment?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Create fragment instances
        // set buttons referenece

         fragmentOne = UsersFragment()
         fragmentTwo = FirstFragment.newInstance("Two")
         fragmentThree = FirstFragment.newInstance("Three")
         fragmentFour = FirstFragment.newInstance("Four")

         buttonOne= findViewById(R.id.btnMaintabOne)
         buttonTwo = findViewById(R.id.btnMaintabTwo)
         buttonThree = findViewById(R.id.btnMaintabThree)
         buttonFour = findViewById(R.id.btnMaintabFour)

        buttonOne?.setOnClickListener {
            fragmentOne?.let {
                displayFragment(it)
            }        }
        buttonTwo?.setOnClickListener {
            fragmentTwo?.let {
                displayFragment(it)
            }        }
        buttonThree?.setOnClickListener {
            fragmentThree?.let {
                displayFragment(it)
            }        }
        buttonFour?.setOnClickListener {
            fragmentFour?.let {
                displayFragment(it)
            }        }
        fragmentOne?.let {
            displayFragment(it)
        }
    }

    fun displayFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.flmainFragment, fragment)

        inDisplayFragment?.let{
            transaction.remove(it)
        }

        transaction.addToBackStack("TAG")
        transaction.commit()
        inDisplayFragment = fragment
    }
}
//    fun removeFirstFragment(){
//        firstFrag?.let {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.remove(it)
//            transaction.addToBackStack("TAG")
//            transaction.commit()
//        }
//    }
//}