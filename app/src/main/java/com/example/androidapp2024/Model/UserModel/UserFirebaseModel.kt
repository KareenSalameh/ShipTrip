package com.example.androidapp2024.Model.UserModel

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class UserFirebaseModel {
    private val db = FirebaseFirestore.getInstance()

    companion object {
        const val USERS_COLLECTION_PATH = "Users"
    }

    fun getUserData(userId: String, onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {
        val userDocRef = db.collection(USERS_COLLECTION_PATH).document(userId)
        userDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userData = documentSnapshot.toObject(User::class.java)
                    onSuccess(userData!!)
                } else {
                    onFailure(Exception("User data not found"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun addUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userDocRef = db.collection(USERS_COLLECTION_PATH).document(user.userId)
        userDocRef.set(user, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}