package com.example.androidapp2024.Modules.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapp2024.R
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var resetPasswordButton: Button
    private lateinit var textViewForgotPass:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailEditText = findViewById(R.id.etEmailForgotPassword)
        resetPasswordButton = findViewById(R.id.btnResetPassword)
        textViewForgotPass = findViewById(R.id.textReturnToLogin)

        resetPasswordButton.setOnClickListener {
            resetPassword()
        }
        textViewForgotPass.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun resetPassword() {
        val email = emailEditText.text.toString().trim()
        if (email.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent to $email", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to send password reset email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
        }
    }
}