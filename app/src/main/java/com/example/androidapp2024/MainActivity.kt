package com.example.androidapp2024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("DEMO", "OnCreate")
        val intent = Intent(this, AddUserActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        Log.d("DEMO", "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("DEMO", "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d("DEMO", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("DEMO", "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEMO", "onDestroy")

    }

}