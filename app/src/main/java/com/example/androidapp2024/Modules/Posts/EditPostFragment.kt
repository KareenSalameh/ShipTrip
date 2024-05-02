package com.example.androidapp2024.Modules.Posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirebaseModel
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
        return view
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

    private fun loadPostData() {
        if (postId != null) {
            FirebaseFirestore.getInstance().collection(PostFirebaseModel.POSTS_COLLECTION_PATH)
                .document(postId!!)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val post = documentSnapshot.toObject(Post::class.java)
                    if (post != null) {
                        populatePostData(post)
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle the failure case
                }
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

    private fun savePost() {
        val itemName = itemNameEditText.text.toString()
        val itemWeight = itemWeightEditText.text.toString()
        val payForShipping = payForShippingCheckBox.isChecked
        val fromLocation = fromLocationEditText.text.toString()
        val toLocation = toLocationEditText.text.toString()
        val itemDescription = itemDescriptionEditText.text.toString()

        if (postId != null) {
            val updatedPost = Post(
                itemName = itemName,
                itemImageUri = "", // You can update the image URI if needed
                itemWeight = itemWeight,
                payForShipping = payForShipping,
                fromLocation = fromLocation,
                toLocation = toLocation,
                itemDescription = itemDescription,
                author = "", // Set the author
                userId = "", // Set the user ID
                postId = postId!!
            )

//            firebaseModel.updatePost(updatedPost) {
//                // Post updated successfully
//                // Navigate back or update the UI as needed
//            }
        }
    }
}