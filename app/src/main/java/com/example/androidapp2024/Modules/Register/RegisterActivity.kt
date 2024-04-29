package com.example.androidapp2024.Modules.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Modules.Login.LoginActivity
import com.example.androidapp2024.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        setupViews()
    }

    private fun setupViews() {
        val registerButton: Button = findViewById(R.id.button3)
        val textViewLogin: TextView = findViewById(R.id.textViewLogin)

        registerButton.setOnClickListener {
            registerUser()
        }

        textViewLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun registerUser() {
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val usernameEditText = findViewById<EditText>(R.id.editTextName)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val username = usernameEditText.text.toString()

        if (isValidInput(email, password)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.let {
                            val newUser = User(it.uid, username, "", email, "")
                            addUserToFirestore(newUser)
                        }
                    } else {
                        Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun addUserToFirestore(user: User) {
        UserFirestore.getInstance().addUser(user,
            onSuccess = {
                navigateToLogin()
            },
            onFailure = { exception ->
                Toast.makeText(this, "Failed to add user data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun isValidInput(email: String, password: String): Boolean {
        return if (!isValidEmail(email)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            false
        } else if (!isValidPassword(password)) {
            Toast.makeText(this, "Invalid password. Password must contain at least 4 characters with letters and numbers.", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}\$".toRegex()
        return pattern.matches(password)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close current activity
    }
}

//package com.example.androidapp2024.Modules.Register
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.TextView
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import com.example.androidapp2024.Model.UserModel.User
//import com.example.androidapp2024.Modules.Login.LoginActivity
//import com.example.androidapp2024.R
//
//class RegisterActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
//
//        auth = FirebaseAuth.getInstance()
//
//        val registerButton: Button = findViewById(R.id.button3)
//        registerButton.setOnClickListener {
//            val emailEditText = findViewById(R.id.editTextEmail) as EditText
//            val passwordEditText = findViewById(R.id.editTextPassword) as EditText
//            val usernameEditText = findViewById(R.id.editTextName) as EditText
//
//            val email = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//            val username = usernameEditText.text.toString()
//
//            // Validate email and password
//            if (!isValidEmail(email)) {
//                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (!isValidPassword(password)) {
//                Toast.makeText(this, "Invalid password. Password must contain at least 4 characters with letters and numbers.", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Check if email and password are not empty
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                auth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            val user = auth.currentUser
//                            user?.let {
//                                val newUser = User(it.uid, username, "", email, "")
//                                UserFirestore.getInstance().addUser(newUser, {
//                                    startActivity(Intent(this, LoginActivity::class.java))
//                                    finish() // Close current activity
//                                }, { exception ->
//                                    Toast.makeText(this, "Failed to add user data: ${exception.message}", Toast.LENGTH_SHORT).show()
//                                })
//                            }
//                        } else {
//                            Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//            } else {
//                Toast.makeText(baseContext, "Please enter email and password.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        val textViewLogin: TextView = findViewById(R.id.textViewLogin)
//        textViewLogin.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun isValidEmail(email: String): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }
//
//    private fun isValidPassword(password: String): Boolean {
//        val pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}\$".toRegex()
//        return pattern.matches(password)
//    }
//}