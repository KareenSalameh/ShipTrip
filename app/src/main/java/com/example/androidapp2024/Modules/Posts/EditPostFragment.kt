package com.example.androidapp2024.Modules.Posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirebaseModel
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.R
import com.google.firebase.firestore.FirebaseFirestore

class EditPostFragment : Fragment() {

    private lateinit var itemImageView: ImageView
    private lateinit var itemNameEditText: EditText
    private lateinit var itemWeightEditText: EditText
    private lateinit var payForShippingCheckBox: CheckBox
    private lateinit var fromLocationEditText: EditText
    private lateinit var toLocationEditText: EditText
    private lateinit var itemDescriptionEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private var post= Post() // Add this variable to store the post data

    private var postId: String? = null
    private lateinit var firebaseModel: PostFirebaseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseModel = PostFirebaseModel()
        arguments?.let {
            postId = it.getString("postId")
            Log.i("Tag","Recieved the ID $postId")
        }
    }
    fun setPostToEdit(post: Post) {
        var postToEdit = post
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)
        initViews(view)
        loadPostData()
        cancelButton = view.findViewById(R.id.btnCancelPost2)
        cancelButton.setOnClickListener {
            cancelEdit()
        }
        saveButton = view.findViewById(R.id.btnSavePost)
        saveButton.setOnClickListener {
            postId?.let { postId ->
                Log.i("Tag","postId: ${postId}")
                saveChanges(postId) // Pass the postId to saveChanges function
            }
        }

        return view
    }

    private fun loadPostData() {
        if (postId != null) {
            PostFirestore.getInstance().getPostData(
                postId!!,
                onSuccess = { post ->
                    this.post = post // Update the post variable with the retrieved post data
                    populatePostData(post) // Populate UI fields with post data
                    Log.e("Tag", " loading post data: ${post}")

                },
                onFailure = { exception ->
                    // Handle failure case, such as displaying an error message
                    Log.e("LoadPostData", "Error loading post data: ${exception.message}")
                }
            )
        }
    }

    private fun initViews(view: View) {
        itemImageView = view.findViewById(R.id.ivPostImage)
        itemNameEditText = view.findViewById(R.id.etItemName)
        itemWeightEditText = view.findViewById(R.id.etItemWeight)
        payForShippingCheckBox = view.findViewById(R.id.cbPayForShipping)
        fromLocationEditText = view.findViewById(R.id.etFromLocation)
        toLocationEditText = view.findViewById(R.id.etToLocation)
        itemDescriptionEditText = view.findViewById(R.id.etItemDescription)

        view.findViewById<View>(R.id.btnSavePost).setOnClickListener {
            savePost()
        }
    }

    private fun populatePostData(post: Post) {
        if (!post.itemImageUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(post.itemImageUri)
                .into(itemImageView)
        }
        itemNameEditText.setText(post.itemName)
        itemWeightEditText.setText(post.itemWeight)
        payForShippingCheckBox.isChecked = post.payForShipping
        fromLocationEditText.setText(post.fromLocation)
        toLocationEditText.setText(post.toLocation)
        itemDescriptionEditText.setText(post.itemDescription)
    }
    private fun cancelEdit() {
        findNavController().popBackStack()
    }
private fun saveChanges(postId: String) {
        val updatedPost = Post(
            postId = postId,
            itemName = itemNameEditText.text.toString().takeIf { it.isNotBlank() } ?: post.itemName,
            itemImageUri = post.itemImageUri,
            itemWeight = itemWeightEditText.text.toString().takeIf { it.isNotBlank() } ?: post.itemWeight,
            payForShipping = payForShippingCheckBox.isChecked,
            fromLocation = fromLocationEditText.text.toString().takeIf { it.isNotBlank() } ?: post.fromLocation,
            toLocation = toLocationEditText.text.toString().takeIf { it.isNotBlank() } ?: post.toLocation,
            itemDescription = itemDescriptionEditText.text.toString().takeIf { it.isNotBlank() } ?: post.itemDescription,
            author = post.author,
            userId = post.userId,
            datePosted = post.datePosted
        )

        PostFirestore.getInstance().updatePost(updatedPost) {
            Toast.makeText(requireContext(), "Post updated successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun savePost() {
        val itemName = itemNameEditText.text.toString()
        val itemWeight = itemWeightEditText.text.toString()
        val payForShipping = payForShippingCheckBox.isChecked
        val fromLocation = fromLocationEditText.text.toString()
        val toLocation = toLocationEditText.text.toString()
        val itemDescription = itemDescriptionEditText.text.toString()

        // Update the post object with the edited data
        post.itemName = itemName
        post.itemWeight = itemWeight
        post.payForShipping = payForShipping
        post.fromLocation = fromLocation
        post.toLocation = toLocation
        post.itemDescription = itemDescription

        // Update the post in Firestore
        PostFirestore.getInstance().updatePost(post){
            // Navigate back to the previous fragment or show a success message
            findNavController().popBackStack()
        }
    }
}