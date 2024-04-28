package com.example.androidapp2024.Modules.Posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.Modules.Posts.PAdapter.PostsRecyclerAdapter
import com.example.androidapp2024.databinding.ActivityPostsRcyclerViewBinding

class PostsRcyclerViewActivity : AppCompatActivity() {

    var postRcyclerView: RecyclerView? = null
    var posts: List<Post>? = null
    var adapter: PostsRecyclerAdapter?= null

    private lateinit var binding: ActivityPostsRcyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostsRcyclerViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        PostFirestore.instance.getAllPosts { posts ->
            this.posts= posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()
        }
//            (object: Model.GetAllUserListener {
//                override fun onComplete(users: List<User>) {
//                    TODO("Not yet implemented")
//                }
//            })


        postRcyclerView = binding.rvPostRcyclerList
        postRcyclerView?.setHasFixedSize(true)
        postRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = PostsRecyclerAdapter(posts)
        adapter?.listener = object : OnItemClickedListener{
            override fun OnItemClick(position: Int) {
                Log.i("TAG", "Position CLicked $position")
            }

            override fun onPostClicked(post: Post?) {
                Log.i("TAG", "Post $post")
            }

        }
        postRcyclerView?.adapter = adapter
    }

    interface OnItemClickedListener {
        fun OnItemClick(position: Int)//user
        fun onPostClicked(post: Post?)

    }

    override fun onResume() {
        super.onResume()
        PostFirestore.instance.getAllPosts { posts ->
            this.posts=posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()
        }
    }
}