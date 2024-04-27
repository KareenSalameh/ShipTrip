package com.example.androidapp2024.Modules.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.example.androidapp2024.Modules.Feed.FeedActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.example.androidapp2024.R
import com.example.androidapp2024.Modules.Register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseModel: UserFirebaseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val loginButton: Button = findViewById(R.id.button3)
        loginButton.setOnClickListener {
            val emailEditText = findViewById(R.id.editTextEmail) as EditText
            val passwordEditText = findViewById(R.id.editTextPassword) as EditText

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            // Validate email and password
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPassword(password)) {
                Toast.makeText(this, "Invalid password. Password must contain at least 4 characters with letters and numbers.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Check if email and password are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Sign in user with email and password
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // val user = auth.currentUser
                            startActivity(Intent(this, FeedActivity::class.java))
                            finish() // Close current activity
                        } else {
                            // Sign in failed
                            when (task.exception) {
                                is FirebaseAuthInvalidUserException -> {
                                    // Email not registered
                                    Toast.makeText(this, "Email not registered.", Toast.LENGTH_SHORT).show()
                                }
                                is FirebaseAuthInvalidCredentialsException -> {
                                    // Incorrect password or email
                                    Toast.makeText(this, "Incorrect email or password.", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    // Other errors
                                    Log.e("LoginActivity", "signInWithEmailAndPassword:failure", task.exception)
                                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
            } else {
                // Display an error message if email or password is empty
                Toast.makeText(
                    baseContext, "Please enter email and password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val textViewRegister: TextView = findViewById(R.id.textViewRegister)
        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}\$".toRegex()
        return pattern.matches(password)
    }
}
//package com.example.androidapp2024
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.TextView
//
//class LoginActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        val textViewLogin: TextView = findViewById(R.id.textViewRegister)
//
//        textViewLogin.setOnClickListener {
//            // Create an Intent to navigate to the RegisterActivity
//            val intent = Intent(this, RegisterActivity::class.java)
//
//            startActivity(intent)
//        }
//    }
//}