package com.example.androidapp2024.Model.PostModel

import com.google.firebase.Timestamp
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings

class PostFirebaseModel {
    private val db = Firebase.firestore

    companion object{
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

    fun addPost(post: Post, callback: () -> Unit) {
        db.collection(POSTS_COLLECTION_PATH).document(post.postUid).set(post.json).addOnSuccessListener {
            callback()
        }
    }
}