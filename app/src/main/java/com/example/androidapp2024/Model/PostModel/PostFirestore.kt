package com.example.androidapp2024.Model.PostModel
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import com.example.androidapp2024.dao.AppLocalDatabase
import java.util.concurrent.Executors

class PostFirestore constructor()  {
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = PostFirebaseModel()
    companion object {
        val instance: PostFirestore = PostFirestore()
    }

    interface GetAllPostListener{
        fun onComplete(posts: List<Post>)
    }
    fun getAllPosts(callback: (List<Post>) -> Unit) {
        val lastUpdated:Long = Post.lastUpdated

        firebaseModel.getAllPosts(lastUpdated){list ->
            Log.i("TAG", "FireBase returned ${list.size}, lastUpdated: $lastUpdated")

            executor.execute{
                var time = lastUpdated
                for(post in list){
                    database.PostDao().insert(post)


                    post.lastUpdated?.let{
                        if(time < it){
                            time = post.lastUpdated ?:System.currentTimeMillis()
                        }
                    }
                    //update local data
                    Post.lastUpdated = time
                    val posts = database.PostDao().getAll()

                    mainHandler.post{
                        callback(posts)

                    }
                }
            }
        }

    }
    fun addPost(post: Post, callback: () -> Unit){
        firebaseModel.addPost(post, callback)

    }


}