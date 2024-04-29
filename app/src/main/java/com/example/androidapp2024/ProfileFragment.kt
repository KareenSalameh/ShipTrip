package com.example.androidapp2024

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
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var locationTextView: TextView

    private lateinit var btnEditProfile: Button
    private lateinit var btnMyPosts: Button
    private lateinit var btnLogout: Button
    private val auth = FirebaseAuth.getInstance()
    private val userFirebaseModel = UserFirebaseModel()
    private val userFirestore = UserFirestore.getInstance()

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
//package com.example.androidapp2024
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import com.bumptech.glide.Glide
//import com.example.androidapp2024.Model.UserModel.User
//import com.google.firebase.auth.FirebaseAuth
//import de.hdodenhof.circleimageview.CircleImageView
//
//class ProfileFragment : Fragment() {
//    private lateinit var profileImage: CircleImageView
//    private lateinit var tvName: TextView
//    private lateinit var tvEmail: TextView
//    private lateinit var tvLocation: TextView
//    private lateinit var btnEditProfile: Button
//    private lateinit var btnMyPosts: Button
//    private lateinit var btnLogout: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//
//        profileImage = view.findViewById(R.id.profileImage)
//        tvName = view.findViewById(R.id.tvNameOfUser2)
//        tvEmail = view.findViewById(R.id.tvEmailOfUser2)
//        tvLocation = view.findViewById(R.id.tvLocationOfUser2)
//        btnEditProfile = view.findViewById(R.id.btnEditProfile)
//        btnMyPosts = view.findViewById(R.id.btnMyPosts)
//        btnLogout = view.findViewById(R.id.btnLogout)
//
//        // Retrieve the user's UID from shared preferences or other storage
//        val userId = getUserIdFromStorage()
//
//        // Retrieve user data from Firestore
//        if (userId != null) {
//            UserFirestore.getInstance().getUserData(userId, ::onUserDataRetrieved, ::onUserDataRetrievalFailed)
//        }
//
//        // Set click listeners for buttons
//        btnEditProfile.setOnClickListener {
//            navigateToEditProfile()
//        }
//
//        btnMyPosts.setOnClickListener {
//            navigateToMyPosts()
//        }
//
//        btnLogout.setOnClickListener {
//            logout()
//        }
//
//        return view
//    }
//
//    private fun onUserDataRetrieved(userData: User) {
//        // Update UI with user data
//        tvName.text = userData.name
//        tvEmail.text = userData.email
//        tvLocation.text = userData.location
//
//        // Load profile image using Glide library
//        if (!userData.userImgUrl.isNullOrEmpty()) {
//            Glide.with(requireContext())
//                .load(userData.userImgUrl)
//                .into(profileImage)
//        } else {
//            // Handle the case when the user doesn't have a profile image
//            profileImage.setImageResource(R.drawable.baseline_add_photo_alternate_24)
//        }
//    }
//
//    private fun onUserDataRetrievalFailed(exception: Exception) {
//        // Handle error case
//        Log.e("ProfileFragment", "Error retrieving user data: ${exception.message}")
//    }
//
//    private fun getUserIdFromStorage(): String? {
//        // Replace this with your actual implementation to retrieve the user's UID
//        // For example, using SharedPreferences:
//        val sharedPreferences = requireActivity().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE)
//        return sharedPreferences.getString("USER_ID", null)
//    }
//
//    private fun navigateToEditProfile() {
//        // Navigate to the edit profile fragment
//        // Example:
//        // val editProfileFragment = EditProfileFragment()
//        // parentFragmentManager.beginTransaction()
//        //     .replace(R.id.container, editProfileFragment)
//        //     .addToBackStack(null)
//        //     .commit()
//    }
//
//    private fun navigateToMyPosts() {
//        // Navigate to the user's posts fragment
//        // Example:
//        // val myPostsFragment = MyPostsFragment()
//        // parentFragmentManager.beginTransaction()
//        //     .replace(R.id.container, myPostsFragment)
//        //     .addToBackStack(null)
//        //     .commit()
//    }
//
//    private fun logout() {
//        // Perform logout operation
//        FirebaseAuth.getInstance().signOut()
//
//        // Clear the user's UID from shared preferences or other storage
//        clearUserIdFromStorage()
//
//        // Navigate to the login screen or perform any other necessary actions
//        val intent = Intent(requireContext(), MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent)
//        requireActivity().finish()
//    }
//
//    private fun clearUserIdFromStorage() {
//        // Replace this with your actual implementation to clear the user's UID from storage
//        // For example, using SharedPreferences:
//        val sharedPreferences = requireActivity().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE)
//        sharedPreferences.edit().remove("USER_ID").apply()
//    }
//}
//package com.example.androidapp2024
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import com.example.androidapp2024.Model.UserModel.User
//import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
//import com.google.firebase.auth.FirebaseAuth
//import de.hdodenhof.circleimageview.CircleImageView
//
//class ProfileFragment : Fragment() {
//    private lateinit var profileImage: CircleImageView
//    private lateinit var tvName: TextView
//    private lateinit var tvEmail: TextView
//    private lateinit var tvLocation: TextView
//    private lateinit var btnEditProfile: Button
//    private lateinit var btnMyPosts: Button
//    private lateinit var btnLogout: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//
//        profileImage = view.findViewById(R.id.profileImage)
//        tvName = view.findViewById(R.id.tvNameOfUser2)
//        tvEmail = view.findViewById(R.id.tvEmailOfUser2)
//        tvLocation = view.findViewById(R.id.tvLocationOfUser2)
//        btnEditProfile = view.findViewById(R.id.btnEditProfile)
//        btnMyPosts = view.findViewById(R.id.btnMyPosts)
//        btnLogout = view.findViewById(R.id.btnLogout)
//
//
//        // Load user data from the signed-in user
//       // loadUserData()
//        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
//
//        // Retrieve user data from Firestore
//        if (currentUserId != null) {
//            UserFirebaseModel().getUserData(currentUserId, ::onUserDataRetrieved, ::onUserDataRetrievalFailed)
//        }
//
//        // Set click listeners for buttons
//        btnEditProfile.setOnClickListener {
//            // Navigate to the edit profile fragment
//            navigateToEditProfile()
//        }
//
//        btnMyPosts.setOnClickListener {
//            // Navigate to the user's posts fragment
//            navigateToMyPosts()
//        }
//
//        btnLogout.setOnClickListener {
//            // Perform logout operation
//            logout()
//        }
//
//        return view
//    }
//    private fun onUserDataRetrieved(userData: User) {
//        // Update UI with user data
//        tvName.text = userData.name
//        tvEmail.text = userData.email
//        tvLocation.text = userData.location
////        if (!userData.userImgUrl.isNullOrEmpty()) {
////            Glide.with(requireContext())
////                .load(userData.userImgUrl)
////                .into(profileImage)
////        } else {
////            // Handle the case when the user doesn't have a profile image
////            profileImage.setImageResource(R.drawable.default_profile_image)
////        }
//      //  tvLocationOfUser2.text = userData.location
//        // ... (update other UI elements as needed)
//    }
//
//    private fun onUserDataRetrievalFailed(exception: Exception) {
//        // Handle error case
//        Log.e("ProfileFragment", "Error retrieving user data: ${exception.message}")
//    }
//    private fun loadUserData() {
//        // Load user data from the signed-in user and populate the views
//        // Example:
//        // val user = FirebaseAuth.getInstance().currentUser
//        // user?.let {
//        //     tvName.text = it.displayName
//        //     tvEmail.text = it.email
//        //     // Load profile image using Glide or Picasso library
//        //     // Glide.with(requireContext()).load(it.photoUrl).into(profileImage)
//        // }
//    }
//
//    private fun navigateToEditProfile() {
//        // Navigate to the edit profile fragment
//        // Example:
//        // val editProfileFragment = EditProfileFragment()
//        // parentFragmentManager.beginTransaction()
//        //     .replace(R.id.container, editProfileFragment)
//        //     .addToBackStack(null)
//        //     .commit()
//    }
//
//    private fun navigateToMyPosts() {
//        // Navigate to the user's posts fragment
//        // Example:
//        // val myPostsFragment = MyPostsFragment()
//        // parentFragmentManager.beginTransaction()
//        //     .replace(R.id.container, myPostsFragment)
//        //     .addToBackStack(null)
//        //     .commit()
//    }
//
//    private fun logout() {
//        // Perform logout operation
//        // Example:
//        // FirebaseAuth.getInstance().signOut()
//        // Navigate to the login screen or perform any other necessary actions
//        val intent = Intent(requireContext(), MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent)
//        requireActivity().finish()
//    }
//}