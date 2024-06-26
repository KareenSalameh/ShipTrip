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
import com.example.androidapp2024.R
import com.example.androidapp2024.Modules.Register.RegisterActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import android.content.SharedPreferences


class LoginActivity : AppCompatActivity() {
    private var auth = Firebase.auth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textViewRegister: TextView
    private lateinit var textViewForgotPass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.Loginbtn2)
        textViewRegister= findViewById(R.id.textViewRegister)
        textViewForgotPass= findViewById(R.id.textViewForgotPass)

        loginButton.setOnClickListener {
            logInUser()
        }
        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        textViewForgotPass.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    private fun logInUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidPassword(password)) {
            Toast.makeText(
                this,
                "Invalid password. Password must contain at least 4 characters with letters and numbers.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this@LoginActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}\$".toRegex()
        return pattern.matches(password)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
