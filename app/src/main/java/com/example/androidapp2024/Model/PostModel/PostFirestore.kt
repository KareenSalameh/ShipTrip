package com.example.androidapp2024.Model.PostModel
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.example.androidapp2024.dao.AppLocalDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.concurrent.Executors

class PostFirestore constructor()  {
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = PostFirebaseModel()
    private val db = FirebaseFirestore.getInstance()
    private val postsCollection = db.collection(PostFirebaseModel.POSTS_COLLECTION_PATH)

    companion object {
       // val instance: PostFirestore = PostFirestore()
        @Volatile
        private var INSTANCE: PostFirestore? = null

        fun getInstance(): PostFirestore {
            return INSTANCE ?: synchronized(this) {
                val instance = PostFirestore()
                INSTANCE = instance
                instance
            }
        }
    }
    interface GetAllPostListener{
        fun onComplete(posts: List<Post>)
    }

    fun getAllPosts(callback: (List<Post>) -> Unit) {
        firebaseModel.getAllPosts(0) { list ->
            executor.execute {
                database.PostDao().deleteAll() // Clear the local database
                for (post in list) {
                    database.PostDao().insert(post) // Insert posts into the local database
                }
                val posts = database.PostDao().getAll()
                mainHandler.post {
                    callback(posts)
                }
            }
        }
    }
    fun deletePost(post: Post, callback: () -> Unit) {
        firebaseModel.deletePost(post) {
            callback()
        }
    }
    fun updatePost(post: Post, callback: () -> Unit){
        firebaseModel.updatePost(post){
            callback()
        }
    }

    fun getPostData(postId: String, onSuccess: (Post) -> Unit, onFailure: (Exception) -> Unit) {
        firebaseModel.getPostData(postId, onSuccess, onFailure)
    }
    fun addPost(post: Post, callback: () -> Unit){
        firebaseModel.addPost(post, callback)

    }

}