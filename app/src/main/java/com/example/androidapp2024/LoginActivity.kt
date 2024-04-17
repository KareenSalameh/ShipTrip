package com.example.androidapp2024

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Find the TextView
        val textViewLogin: TextView = findViewById(R.id.textViewRegister)

        // Set OnClickListener for the TextView
        textViewLogin.setOnClickListener {
            // Create an Intent to navigate to the RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)

            // Start the RegisterActivity
            startActivity(intent)
        }
    }
}