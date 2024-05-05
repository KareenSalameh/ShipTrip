package com.example.androidapp2024.Modules.Posts.MyAdapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.Modules.Posts.EditPostFragment
import com.example.androidapp2024.Modules.Posts.PAdapter.PostsRecyclerAdapter
import com.example.androidapp2024.Modules.Posts.PostsRcyclerViewActivity
import com.example.androidapp2024.R
import com.google.firebase.auth.FirebaseAuth

class MyPostsFragment : Fragment() {
    private lateinit var postsAdapter: PostsRecyclerAdapter
    private lateinit var postsList: MutableList<Post>
    private var rvMyPostsRcycler: RecyclerView? = null
    private var onItemClickedListener: PostsRcyclerViewActivity.OnItemClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMyPostsRcycler = view.findViewById(R.id.rvMyPostsRcycler)
        setupRecyclerView()
        fetchUserPosts()

        onItemClickedListener = object : PostsRcyclerViewActivity.OnItemClickedListener {
            override fun OnItemClick(position: Int) {
                Log.i("TAG", "Position Clicked $position")
            }

            override fun onPostClicked(post: Post?) {
                // TODO: Implement post click action if needed
            }

            override fun onDeletePostClicked(post: Post?) {
                TODO("Not yet implemented")
            }

            override fun onPostDeleted(post: Post) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun setupRecyclerView() {
        postsList = mutableListOf()
        postsAdapter = PostsRecyclerAdapter(postsList, true)
        rvMyPostsRcycler?.layoutManager = LinearLayoutManager(requireContext())
        rvMyPostsRcycler?.adapter = postsAdapter
        postsAdapter.listener = onItemClickedListener
    }


    private fun fetchUserPosts() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {
            PostFirestore.getInstance().getAllPosts { posts ->
                postsList.clear()
                val userPosts = posts.filter { it.userId == currentUserId }
                if (userPosts.isNotEmpty()) {
                    postsList.addAll(userPosts)
                    postsAdapter.notifyDataSetChanged()
                } else {
                    displayNoPostsMessage()
                }
            }
        }
    }

    private fun displayNoPostsMessage() {
        val parentLayout = rvMyPostsRcycler?.parent as? ViewGroup
        parentLayout?.removeView(rvMyPostsRcycler)
        val noPostsTextView = TextView(requireContext()).apply {
            text = "No Posts created yet"
            textSize = 18f
            setPadding(20, 0, 0, 0)
            setTextColor(resources.getColor(android.R.color.black, null))
        }
        parentLayout?.addView(noPostsTextView)
    }
}