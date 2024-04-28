package com.example.androidapp2024

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    private lateinit var profileImage: CircleImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvLocation: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var btnMyPosts: Button
    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileImage = view.findViewById(R.id.profileImage)
        tvName = view.findViewById(R.id.tvNameOfUser2)
        tvEmail = view.findViewById(R.id.tvEmailOfUser2)
       // tvLocation = view.findViewById(R.id.tvLocationOfUser2)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)
        btnMyPosts = view.findViewById(R.id.btnMyPosts)
        btnLogout = view.findViewById(R.id.btnLogout)


        // Load user data from the signed-in user
        loadUserData()

        // Set click listeners for buttons
        btnEditProfile.setOnClickListener {
            // Navigate to the edit profile fragment
            navigateToEditProfile()
        }

        btnMyPosts.setOnClickListener {
            // Navigate to the user's posts fragment
            navigateToMyPosts()
        }

        btnLogout.setOnClickListener {
            // Perform logout operation
            logout()
        }

        return view
    }

    private fun loadUserData() {
        // Load user data from the signed-in user and populate the views
        // Example:
        // val user = FirebaseAuth.getInstance().currentUser
        // user?.let {
        //     tvName.text = it.displayName
        //     tvEmail.text = it.email
        //     // Load profile image using Glide or Picasso library
        //     // Glide.with(requireContext()).load(it.photoUrl).into(profileImage)
        // }
    }

    private fun navigateToEditProfile() {
        // Navigate to the edit profile fragment
        // Example:
        // val editProfileFragment = EditProfileFragment()
        // parentFragmentManager.beginTransaction()
        //     .replace(R.id.container, editProfileFragment)
        //     .addToBackStack(null)
        //     .commit()
    }

    private fun navigateToMyPosts() {
        // Navigate to the user's posts fragment
        // Example:
        // val myPostsFragment = MyPostsFragment()
        // parentFragmentManager.beginTransaction()
        //     .replace(R.id.container, myPostsFragment)
        //     .addToBackStack(null)
        //     .commit()
    }

    private fun logout() {
        // Perform logout operation
        // Example:
        // FirebaseAuth.getInstance().signOut()
        // Navigate to the login screen or perform any other necessary actions
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
}