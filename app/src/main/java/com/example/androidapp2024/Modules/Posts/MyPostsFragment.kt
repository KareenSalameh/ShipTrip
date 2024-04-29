package com.example.androidapp2024.Modules.Posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.Modules.Posts.PAdapter.PostsRecyclerAdapter
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
                Log.i("TAG", "Post $post")
            }
        }
    }

    private fun setupRecyclerView() {
        postsList = mutableListOf()
        postsAdapter = PostsRecyclerAdapter(postsList)
        rvMyPostsRcycler?.layoutManager = LinearLayoutManager(requireContext())
        rvMyPostsRcycler?.adapter = postsAdapter
        postsAdapter.listener = onItemClickedListener
    }

    private fun fetchUserPosts() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {
            PostFirestore.instance.getAllPosts { posts ->
                postsList.clear()
                postsList.addAll(posts.filter { it.userId == currentUserId })
                postsAdapter.notifyDataSetChanged()
            }
        }
    }
}
//package com.example.androidapp2024.Modules.Posts
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.androidapp2024.R
//
//class MyPostsFragment : Fragment() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_my_posts, container, false)
//    }
//}