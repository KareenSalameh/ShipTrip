package com.example.androidapp2024


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.androidapp2024.Modules.Login.LoginActivity
import com.example.androidapp2024.Modules.Register.RegisterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the Login and Signup buttons
        val loginBtn: Button = findViewById(R.id.Loginbtn)
        val signupBtn: Button = findViewById(R.id.Signupbtn)

        // Set OnClickListener for the Login button
        loginBtn.setOnClickListener {
            // Create an Intent to navigate to the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set OnClickListener for the Signup button
        signupBtn.setOnClickListener {
            // Create an Intent to navigate to the RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}




