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
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.google.firebase.storage.FirebaseStorage

class EditProfileFragment : Fragment() {
    private lateinit var btnSave: Button
    private lateinit var btnCancel2: Button
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etLocation: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var btnSelectImage: Button
    private val IMAGE_PICK_CODE = 1000
    private var imageUri: Uri? = null
    private var profileImage: ImageView?= null

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

        btnSave = view.findViewById(R.id.btnSave)
        btnCancel2 = view.findViewById(R.id.btnCancel2)
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etLocation = view.findViewById(R.id.etLocation)
        btnSelectImage = view.findViewById(R.id.btnSelectImage)
        profileImage = view.findViewById(R.id.profileImage)
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
        btnSelectImage.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            // Upload the selected image to Firestore Storage
            uploadImageToStorage(imageUri)
        }
    }

    private fun uploadImageToStorage(imageUri: Uri?) {
        if (imageUri != null) {
            btnSave.isEnabled = false // Disable the save button until upload is complete
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("profile_images/${System.currentTimeMillis()}.jpg")
            val uploadTask = imageRef.putFile(imageUri)

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    val exception = task.exception
                    Log.e("EditProfileFragment", "Error uploading image: ${exception?.message}", exception)
                    throw exception!!
                }
                imageRef.downloadUrl
            }.addOnCompleteListener { task ->
                btnSave.isEnabled = true // Enable the save button
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    updateUserProfile(downloadUri)
                } else {
                    val exception = task.exception
                    Log.e("EditProfileFragment", "Error uploading image: ${exception?.message}", exception)
                }
            }
        }
    }

    private fun updateUserProfile(imageUrl: Uri?) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Fetch user data
            UserFirestore.getInstance().getUserData(currentUser.uid, { userData ->
                // Create an updated User object
                val updatedUser = User(
                    userId = currentUser.uid,
                    name = etName.text.toString().ifBlank { userData.name },
                    userImgUrl = imageUrl?.toString() ?: userData.userImgUrl, // Use the new imageUrl if available, otherwise keep the old one
                    email = etEmail.text.toString().ifBlank { userData.email },
                    location = etLocation.text.toString().ifBlank { userData.location }
                )

                // Update user in Firestore
                UserFirestore.getInstance().updateUser(updatedUser,
                    onSuccess = {
                        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                       // findNavController().popBackStack()
                    },
                    onFailure = { exception ->
                        Toast.makeText(requireContext(), "Failed to update profile: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }, { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve user data: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(requireContext(), "No user is currently authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveChanges() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            UserFirestore.getInstance().getUserData(currentUser.uid, { userData ->
                val updatedUser = User(
                    userId = userData.userId,
                    name = etName.text.toString().ifBlank { userData.name },
                    userImgUrl = userData.userImgUrl,
                    email = etEmail.text.toString().ifBlank { userData.email },
                    location = etLocation.text.toString().ifBlank { userData.location }
                )

                UserFirestore.getInstance().updateUser(updatedUser, onSuccess = {
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                   // updateProfileUI(updatedUser)
                    findNavController().popBackStack()
                }, onFailure = { exception ->
                    Toast.makeText(requireContext(), "Failed to update profile: ${exception.message}", Toast.LENGTH_SHORT).show()
                })
            }, { exception ->
                Toast.makeText(requireContext(), "Failed to retrieve user data: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(requireContext(), "No user is currently authenticated", Toast.LENGTH_SHORT).show()
        }
    }
//    private fun updateProfileUI(user: User) {
//        // Update EditText fields with user data
//        etName.setText(user.name)
//        etEmail.setText(user.email)
//        etLocation.setText(user.location)
//
//        // Load the user image into ImageView using Glide or another image loading library
//        Glide.with(this)
//            .load(user.userImgUrl)
////            .into(profileImage!!)
//    }
}