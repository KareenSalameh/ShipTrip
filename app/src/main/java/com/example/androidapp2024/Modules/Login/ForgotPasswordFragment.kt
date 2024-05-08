//package com.example.androidapp2024.Modules.Login
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.example.androidapp2024.R
//import com.google.firebase.auth.FirebaseAuth
//
//class ForgotPasswordFragment : Fragment() {
//
//    private lateinit var emailEditText: EditText
//    private lateinit var resetPasswordButton: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)
//
//        emailEditText = view.findViewById(R.id.etEmailForgotPassword)
//        resetPasswordButton = view.findViewById(R.id.btnResetPassword)
//
//        resetPasswordButton.setOnClickListener {
//            resetPassword()
//        }
//
//        return view
//    }
//
//    private fun resetPassword() {
//        val email = emailEditText.text.toString().trim()
//
//        if (email.isNotEmpty()) {
//            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(
//                            requireContext(),
//                            "Password reset email sent to $email",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            "Failed to send password reset email: ${task.exception?.message}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//        } else {
//            Toast.makeText(requireContext(), "Please enter your email address", Toast.LENGTH_SHORT)
//                .show()
//        }
//    }
//}