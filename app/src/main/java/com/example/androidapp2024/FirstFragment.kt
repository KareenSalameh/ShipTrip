package com.example.androidapp2024

import android.os.Bundle
//import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirebaseModel
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class FirstFragment : Fragment() {
    private lateinit var profileImageView: CircleImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        profileImageView = view.findViewById(R.id.profileImage)
        tvName = view.findViewById(R.id.tvNameOfUser2)
        tvEmail = view.findViewById(R.id.tvEmailOfUser2)

        val postId = arguments?.getString("postId")
        if (postId != null) {
            fetchPostData(postId)
        }

        return view
    }

    private fun fetchPostData(postId: String) {
        FirebaseFirestore.getInstance().collection(PostFirebaseModel.POSTS_COLLECTION_PATH)
            .document(postId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val post = documentSnapshot.toObject(Post::class.java)
                if (post != null) {
                    fetchUserData(post.userId)
                }
            }
            .addOnFailureListener { exception ->
                // Handle the failure case
            }
    }

    private fun fetchUserData(userId: String) {
        UserFirestore.getInstance().getUserData(userId, { user ->
            // Update UI with user data
            tvName.text = user.name
            tvEmail.text = user.email
            // Load profile image using Glide or Picasso library
        }, { exception ->
            // Handle error retrieving user data
        })
    }
}