package com.example.androidapp2024.Modules.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Modules.Login.LoginActivity
import com.example.androidapp2024.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseModel: UserFirebaseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        firebaseModel = UserFirebaseModel()

        val registerButton: Button = findViewById(R.id.button3)
        registerButton.setOnClickListener {
            val emailEditText = findViewById(R.id.editTextEmail) as EditText
            val passwordEditText = findViewById(R.id.editTextPassword) as EditText
            val usernameEditText = findViewById(R.id.editTextName) as EditText

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val username = usernameEditText.text.toString()

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
                // Create user with email and password
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.let {
                                val newUser = User(username, it.uid, "", false)
                                firebaseModel.addUser(newUser) {
                                    // Successfully added user data to Firestore
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish() // Close current activity
                                }
                            }
                        }else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

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

        val textViewLogin: TextView = findViewById(R.id.textViewLogin)
        textViewLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
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
//class RegisterActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
//
//        // Find the TextView
//        val textViewRegister: TextView = findViewById(R.id.textViewLogin)
//
//        // Set OnClickListener for the TextView
//        textViewRegister.setOnClickListener {
//            // Create an Intent to navigate to the LoginActivity
//            val intent = Intent(this, LoginActivity::class.java)
//
//            // Start the LoginActivity
//            startActivity(intent)
//        }
//    }
//}