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
//    fun getAllPosts(callback: (List<Post>) -> Unit) {
//        val lastUpdated:Long = Post.lastUpdated
//
//        firebaseModel.getAllPosts(lastUpdated){list ->
//            Log.i("TAG", "FireBase returned ${list.size}, lastUpdated: $lastUpdated")
//
//            executor.execute{
//                var time = lastUpdated
//                for(post in list){
//                    database.PostDao().insert(post)
//
//                    post.lastUpdated?.let{
//                        if(time < it){
//                            time = post.lastUpdated ?:System.currentTimeMillis()
//                        }
//                    }
//                    //update local data
//                    Post.lastUpdated = time
//                    val posts = database.PostDao().getAll()
//
//                    mainHandler.post{
//                        callback(posts)
//
//                    }
//                }
//            }
//        }
//
//    }

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
    fun deletePost(postId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseModel.deletePost(postId, onSuccess, onFailure)
    }
    fun updatePost(post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val postDocRef = postsCollection.document(post.postId)
        postDocRef.set(post, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
//
    fun getPostData(postId: String, onSuccess: (Post) -> Unit, onFailure: (Exception) -> Unit) {
        // Step 1: Check if the post data is available in the local database
        val localPost = database.PostDao().getPostById(postId)
            // Step 2: Fetch the post data from Firebase
            val postDocRef = postsCollection.document(postId)
            postDocRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val postData = documentSnapshot.toObject(Post::class.java)
                        postData?.let {
                            // Step 3: Insert the fetched post data into the local database
                            executor.execute {
                                database.PostDao().insert(it)
                                mainHandler.post {
                                    onSuccess(it)
                                }
                            }
                        } ?: onFailure(Exception("Post data not found"))
                    } else {
                        onFailure(Exception("Post data not found"))
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
    }


    fun getPostById(postId: String, onSuccess: (Post) -> Unit, onFailure: (Exception) -> Unit) {
        firebaseModel.getPostById(postId, onSuccess, onFailure)
    }
    fun addPost(post: Post, callback: () -> Unit){
        firebaseModel.addPost(post, callback)

    }


}