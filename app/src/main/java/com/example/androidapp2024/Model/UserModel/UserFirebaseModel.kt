package com.example.androidapp2024.Model.UserModel

import com.google.firebase.Timestamp
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings

class UserFirebaseModel {
    private val db = Firebase.firestore

    companion object{
        const val STUDENTS_COLLECTION_PATH = "Users"
    }
    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }
            db.firestoreSettings = settings
    }
    fun getAllStudents(since: Long,callback: (List<User>) -> Unit){

        db.collection(STUDENTS_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(User.LAST_UPDATED,Timestamp(since,0))
            .get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    val users: MutableList<User> = mutableListOf()
                    for (json in it.result) {

                        val user = User.fromJSON(json.data)
                        users.add(user)
                    }
                    callback(users)
                }
                false -> callback(listOf())
            }
        }
    }

    fun addUser(user: User, callback: () -> Unit) {
        db.collection(STUDENTS_COLLECTION_PATH).document(user.id).set(user.json).addOnSuccessListener {
            callback()
        }
    }

}