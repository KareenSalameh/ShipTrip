package com.example.androidapp2024.Modules.Posts

import android.app.AlertDialog
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

        PostFirestore.getInstance().getAllPosts { posts ->
            this.posts= posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()
        }


        postRcyclerView = binding.rvPostRcyclerList
        postRcyclerView?.setHasFixedSize(true)
        postRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = PostsRecyclerAdapter(posts,false)
        adapter?.listener = object : OnItemClickedListener{
            override fun OnItemClick(position: Int) {
                Log.i("TAG", "Position CLicked $position")
            }

            override fun onPostClicked(post: Post?) {
                Log.i("TAG", "Post $post")

            }

            override fun onDeletePostClicked(post: Post?) {
                post?.let { postToDelete ->
                    Log.i("TAG","onDeletePostClicked ${post}")
                    adapter?.removePost(postToDelete.postId)
                }
            }
            override fun onPostDeleted(post: Post) {
                PostFirestore.getInstance().deletePost(post) {
                    // Post deleted successfully
                    Log.i("TAG","onPostDeleted ${post}")
                    adapter?.removePost(post.postId)
                }
            }

        }

        postRcyclerView?.adapter = adapter
    }

    interface OnItemClickedListener {
        fun OnItemClick(position: Int)//user
        fun onPostClicked(post: Post?)
        fun onDeletePostClicked(post: Post?)
        fun onPostDeleted(post: Post)
    }

    override fun onResume() {
        super.onResume()
        PostFirestore.getInstance().getAllPosts { posts ->
            this.posts=posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()
        }
    }

    private fun showDeleteConfirmationDialog(post: Post?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Post")
        builder.setMessage("Are you sure you want to delete the post?")
        builder.setPositiveButton("Delete") { _, _ ->
            deletePost(post)
        }
        builder.setNegativeButton("Cancel") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }
    private fun deletePost(post: Post?) {
        post?.let { postToDelete ->
            PostFirestore.getInstance().deletePost(postToDelete) {

                // Post deleted successfully
                // Refresh the posts list
                PostFirestore.getInstance().getAllPosts { posts ->
                    this.posts = posts
                    adapter?.posts = posts
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }
}
