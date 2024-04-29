package com.example.androidapp2024.Modules.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.androidapp2024.Model.UserModel.User
import androidx.navigation.fragment.findNavController
import com.example.androidapp2024.R
import com.google.firebase.auth.FirebaseAuth

class EditProfileFragment : Fragment() {
    private lateinit var btnSave: Button
    private lateinit var btnCancel2: Button
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etLocation: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // Find views by their IDs
        btnSave = view.findViewById(R.id.btnSave)
        btnCancel2 = view.findViewById(R.id.btnCancel2)
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etLocation = view.findViewById(R.id.etLocation)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSave.setOnClickListener {
            saveChanges()
        }
        btnCancel2.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun saveChanges() {
        val newName = etName?.text.toString()
        val newEmail = etEmail?.text.toString()
        val newLocation = etLocation?.text.toString()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val updatedUser = User(currentUser.uid, newName ?: "", "", newEmail ?: "", newLocation ?: "")
            UserFirestore.getInstance().updateUser(updatedUser,
                onSuccess = {
                    // Handle successful update
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack() // Navigate back to ProfileFragment
                },
                onFailure = { exception ->
                    // Handle update failure
                    Toast.makeText(requireContext(), "Failed to update profile: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            // Handle the case where no user is currently authenticated
            Toast.makeText(requireContext(), "No user is currently authenticated", Toast.LENGTH_SHORT).show()
        }
    }

}