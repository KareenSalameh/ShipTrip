package com.example.androidapp2024.Model.PostModel

import android.util.Log
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.storage.storage

class PostFirebaseModel {
    private val db = com.google.firebase.Firebase.firestore
    private val storage = com.google.firebase.Firebase.storage
    companion object {
        const val POSTS_COLLECTION_PATH = "Posts"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }
        db.firestoreSettings = settings
    }
    fun getAllPosts(since: Long,callback: (List<Post>) -> Unit){

        db.collection(POSTS_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(Post.LAST_UPDATED,Timestamp(since,0))
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val posts: MutableList<Post> = mutableListOf()
                        for (json in it.result) {

                            val post = Post.fromJSON(json.data)
                            posts.add(post)
                        }
                        callback(posts)
                    }
                    false -> callback(listOf())
                }
            }
    }
    fun getPostData(postId: String, onSuccess: (Post) -> Unit, onFailure: (Exception) -> Unit) {
        Log.d("PostFirebaseModel", "Fetching post data for postId: $postId")
        val userDocRef = db.collection(PostFirebaseModel.POSTS_COLLECTION_PATH).document(postId)
        userDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userData = documentSnapshot.toObject(Post::class.java)
                    onSuccess(userData!!)
                } else {
                    onFailure(Exception("Post data not found"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getPostById(postId: String, onSuccess: (Post) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection(POSTS_COLLECTION_PATH)
            .document(postId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val post = documentSnapshot.toObject(Post::class.java)
                if (post != null) {
                    onSuccess(post)
                } else {
                    onFailure(Exception("Post not found"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
    fun deletePost(post: Post, callback: () -> Unit) {
//        db.collection(POSTS_COLLECTION_PATH)
//            .document(post.postId)
//            .update(post.deleteJson)
//            .addOnSuccessListener {
//                callback()
//            }.addOnFailureListener {
//                Log.d("Error", "Can't delete this post: " + it.message)
//            }
        db.collection(POSTS_COLLECTION_PATH)
            .document(post.postId)
            .delete()
            .addOnCompleteListener {
                callback()
            }
    }

    fun updatePost(post: Post, callback: () -> Unit) {
        db.collection(POSTS_COLLECTION_PATH)
            .document(post.postId)
            .update(post.updateJson)
            .addOnSuccessListener {
                callback()
            }
            .addOnFailureListener { exception ->
                // Handle the failure case
                Log.e("Error", "Error updating post: ${exception.message}", exception)
            }
    }

    fun addPost(post: Post, callback: () -> Unit) {
        db.collection(POSTS_COLLECTION_PATH)
            .document(post.postId)
            .set(post.json)
            .addOnSuccessListener {
                callback()
            }
            .addOnFailureListener { exception ->
                // Handle the failure case
                Log.e("PostFirebaseModel", "Error adding post: ${exception.message}", exception)
            }
    }
}
//package com.example.androidapp2024.Model.PostModel
//
//import android.util.Log
//import com.google.firebase.Timestamp
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.firestore.firestoreSettings
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.firestore.memoryCacheSettings
//
//class PostFirebaseModel {
//    private val db = FirebaseFirestore.getInstance()
//
//    companion object {
//        const val POSTS_COLLECTION_PATH = "Posts"
//    }
//
//    init {
//        val settings = firestoreSettings {
//            setLocalCacheSettings(memoryCacheSettings {  })
//        }
//        db.firestoreSettings = settings
//    }
//    fun getAllPosts(since: Long,callback: (List<Post>) -> Unit){
//
//        db.collection(POSTS_COLLECTION_PATH)
//            .whereGreaterThanOrEqualTo(Post.LAST_UPDATED,Timestamp(since,0))
//            .get().addOnCompleteListener {
//                when (it.isSuccessful) {
//                    true -> {
//                        val posts: MutableList<Post> = mutableListOf()
//                        for (json in it.result) {
//
//                            val post = Post.fromJSON(json.data)
//                            posts.add(post)
//                        }
//                        callback(posts)
//                    }
//                    false -> callback(listOf())
//                }
//            }
//    }
//    fun getPostById(postId: String, onSuccess: (Post) -> Unit, onFailure: (Exception) -> Unit) {
//        db.collection(POSTS_COLLECTION_PATH)
//            .document(postId)
//            .get()
//            .addOnSuccessListener { documentSnapshot ->
//                val post = documentSnapshot.toObject(Post::class.java)
//                if (post != null) {
//                    onSuccess(post)
//                } else {
//                    onFailure(Exception("Post not found"))
//                }
//            }
//            .addOnFailureListener { exception ->
//                onFailure(exception)
//            }
//    }
//    fun deletePost(postId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
//        db.collection(POSTS_COLLECTION_PATH)
//            .document(postId)
//            .delete()
//            .addOnSuccessListener {
//                onSuccess()
//            }
//            .addOnFailureListener { exception ->
//                onFailure(exception)
//            }
//    }
//
//    fun updatePost(post: Post, callback: () -> Unit) {
//        db.collection(POSTS_COLLECTION_PATH)
//            .document(post.postId)
//            .set(post.json)
//            .addOnSuccessListener {
//                callback()
//            }
//            .addOnFailureListener { exception ->
//                // Handle the failure case
//                Log.e("PostFirebaseModel", "Error updating post: ${exception.message}", exception)
//            }
//    }
//
//    fun addPost(post: Post, callback: () -> Unit) {
//        db.collection(POSTS_COLLECTION_PATH)
//            .document(post.postId)
//            .set(post.json)
//            .addOnSuccessListener {
//                callback()
//            }
//            .addOnFailureListener { exception ->
//                // Handle the failure case
//                Log.e("PostFirebaseModel", "Error adding post: ${exception.message}", exception)
//            }
//    }
//}