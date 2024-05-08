package com.example.androidapp2024.Modules.Profile

import UserFirestore
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.androidapp2024.MainActivity
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.example.androidapp2024.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var btnMyPosts: Button
    private lateinit var btnLogout: Button
    private val auth = FirebaseAuth.getInstance()
    private val userFirestore = UserFirestore.getInstance()

    private var itemImageView: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        nameTextView = view.findViewById(R.id.tvNameOfUser2)
        emailTextView = view.findViewById(R.id.tvEmailOfUser2)
        locationTextView = view.findViewById(R.id.tvLocationOfUser2)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)
        btnMyPosts = view.findViewById(R.id.btnMyPosts)
        btnLogout = view.findViewById(R.id.btnLogout)

        itemImageView = view.findViewById(R.id.profileImage)
        btnEditProfile.setOnClickListener {
            navigateToEditProfile()
        }

        btnMyPosts.setOnClickListener {
            navigateToMyPosts()
        }

        btnLogout.setOnClickListener {
            logout()
        }
        nameTextView.text = ""
        emailTextView.text = ""
        locationTextView.text = ""
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = auth.currentUser?.uid

        if (userId != null) {
            fetchUserData(userId)
        } else {
            Log.e("TAG", "User ID is null")
        }
    }
    private fun fetchUserData(userId: String) {
        userFirestore.getUserData(userId,
            onSuccess = { user ->
                displayUserData(user)
            },
            onFailure = { exception ->
                Log.e("TAG", "Failed to fetch user data", exception)
            }
        )
    }

    private fun displayUserData(user: User) {
        nameTextView.text = user.name
        emailTextView.text = user.email
        locationTextView.text = user.location
        Glide.with(requireContext())
            .load(user.userImgUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .error(R.drawable.baseline_account_circle_24)
            .into(itemImageView!!)
    }

    private fun navigateToEditProfile() {
        findNavController().navigate(R.id.action_global_editProfileFragment)
    }

    private fun navigateToMyPosts() {
        findNavController().navigate(R.id.action_global_myPostsFragment)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        clearUserIdFromStorage()
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun clearUserIdFromStorage() {
        val sharedPreferences = requireActivity().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("USER_ID").apply()
    }
}