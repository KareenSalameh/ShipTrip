package com.example.androidapp2024.Modules.Posts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.storage.FirebaseStorage

class AddPostFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    val postId = db.collection("Posts").document().id
    // val userId = db.collection("User").document().id

    private val IMAGE_PICK_CODE = 1000
    private var imageUri: Uri? = null
    private var itemImageView: ImageView? = null
    private var itemNameEditText: EditText? = null
    private var itemWeightEditText: EditText? = null
    private var payForShippingSwitch: Switch? = null
    private var fromLocationEditText: EditText? = null
    private var toLocationEditText: EditText? = null
    private var itemDescriptionEditText: EditText? = null
    private var addPostButton: Button? = null
    private var cancelButton: Button? = null
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            itemImageView?.setImageURI(imageUri)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        itemImageView = view.findViewById(R.id.ivItemImage)
        itemImageView?.setOnClickListener {
            openImagePicker()
        }
//        itemImageView?.setOnClickListener {
//            openImagePicker()
//        }
        itemNameEditText = view.findViewById(R.id.etItemName)
        itemWeightEditText = view.findViewById(R.id.etItemWeight)
        //  payForShippingSwitch = view.findViewById(R.id.swPayForShipping)
        fromLocationEditText = view.findViewById(R.id.etFromLocation)
        toLocationEditText = view.findViewById(R.id.etToLocation)
        itemDescriptionEditText = view.findViewById(R.id.etItemDescription)
        addPostButton = view.findViewById(R.id.btnAddPost)
        cancelButton = view.findViewById(R.id.button)

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        addPostButton?.setOnClickListener {
            val itemName = itemNameEditText?.text.toString()
            val itemWeight = itemWeightEditText?.text.toString()
            val payForShipping = payForShippingSwitch?.isChecked ?: false
            val fromLocation = fromLocationEditText?.text.toString()
            val toLocation = toLocationEditText?.text.toString()
            val itemDescription = itemDescriptionEditText?.text.toString()

            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            if (currentUserId != null) {
                if (imageUri != null) {
                    val storageRef = FirebaseStorage.getInstance().reference
                    val imageRef = storageRef.child("post_images/${System.currentTimeMillis()}.jpg")
                    val uploadTask = imageRef.putFile(imageUri!!)
                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            val exception = task.exception
                            Log.e(
                                "AddPostFragment",
                                "Error uploading image: ${exception?.message}",
                                exception
                            )
                            throw exception!!
                        }
                        imageRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            val post = Post(
                                itemName = itemName,
                                itemImageUri = downloadUri.toString(),
                                itemWeight = itemWeight,
                                payForShipping = payForShipping,
                                fromLocation = fromLocation,
                                toLocation = toLocation,
                                itemDescription = itemDescription,
                                author = "", // Set the author
                                userId = currentUserId,
                                postId = postId
                            )
                            PostFirestore().addPost(post) {
                                activity?.runOnUiThread {
                                    Navigation.findNavController(view).popBackStack()
                                }
                            }
                        } else {
                            val exception = task.exception
                            Log.e(
                                "AddPostFragment",
                                "Error uploading image: ${exception?.message}",
                                exception
                            )

                        }
                    }

                } else {
                    val post = Post(
                        itemName = itemName,
                        itemImageUri = "", // Set the itemImageUri
                        itemWeight = itemWeight,
                        payForShipping = payForShipping,
                        fromLocation = fromLocation,
                        toLocation = toLocation,
                        itemDescription = itemDescription,
                        author = "", // Set the author
                        userId = currentUserId,
                        postId = postId
                    )

                    PostFirestore().addPost(post) {
                        // Navigate back after the post  added successfully
                        activity?.runOnUiThread {
                            Navigation.findNavController(view).popBackStack()
                        }
                    }
                }
            }else {
                // Handle the case when the user is not logged in
                Log.e("AddPostFragment", "User is not logged in")
            }
        }
    }

}