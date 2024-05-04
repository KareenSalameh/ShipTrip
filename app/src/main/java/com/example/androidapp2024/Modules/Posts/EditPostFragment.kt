package com.example.androidapp2024.Modules.Posts

import android.os.Bundle
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
        postId = arguments?.getString("postId")
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
            saveChanges()
        }
        return view
    }

    private fun loadPostData() {
        if (postId != null) {
            FirebaseFirestore.getInstance().collection(PostFirebaseModel.POSTS_COLLECTION_PATH)
                .document(postId!!)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val retrievedPost = documentSnapshot.toObject(Post::class.java)
                    retrievedPost?.let {
                        post = it // Update the post variable with the retrieved post data
                        populatePostData(post) // Populate UI fields with post data
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle the failure case
                }
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

//    private fun loadPostData() {
//        if (postId != null) {
//            FirebaseFirestore.getInstance().collection(PostFirebaseModel.POSTS_COLLECTION_PATH)
//                .document(postId!!)
//                .get()
//                .addOnSuccessListener { documentSnapshot ->
//                    val retrievedPost = documentSnapshot.toObject(Post::class.java)
//                    retrievedPost?.let {
//                        // Populate the post variable with the retrieved post data
//                        post = it
//                        // Populate the UI fields with the retrieved post data
//                        populatePostData(post)
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    // Handle the failure case
//                }
//        }
//    }

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
    private fun saveChanges() {
        PostFirestore.getInstance().getPostData(post.postId, { postData ->
            val updatedPost = Post(
                postId = postData.postId,
                itemName = itemNameEditText.text.toString().takeIf { it.isNotBlank() } ?: postData.itemName,
                itemImageUri = postData.itemImageUri,
                itemWeight = postData.itemWeight,
                payForShipping = postData.payForShipping,
                fromLocation = postData.fromLocation,
                toLocation = postData.toLocation,
                itemDescription = postData.itemDescription,
                author = postData.author,
                userId = postData.userId,
                datePosted = postData.datePosted,
                lastUpdated = postData.lastUpdated
            )

            PostFirestore.getInstance().updatePost(updatedPost, onSuccess = {
                Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }, onFailure = { exception ->
                Toast.makeText(requireContext(), "Failed to update profile: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        }, { exception ->
            Toast.makeText(requireContext(), "Failed to retrieve user data: ${exception.message}", Toast.LENGTH_SHORT).show()
        })
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
        PostFirestore.getInstance().updatePost(post,
            onSuccess = {
                // Navigate back to the previous fragment or show a success message
                findNavController().popBackStack()
            },
            onFailure = { exception ->
                // Handle the failure case
                Toast.makeText(requireContext(), "Failed to update post: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }


}