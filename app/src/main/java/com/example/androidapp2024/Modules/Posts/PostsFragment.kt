package com.example.androidapp2024.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.UserModel.UserFirestore
import com.example.androidapp2024.Modules.Posts.PAdapter.PostsRecyclerAdapter
import com.example.androidapp2024.databinding.FragmentPostsBinding

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private var postsRecyclerView: RecyclerView? = null
    private var posts: List<Post>? = null
    private var adapter: PostsRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.rPostRcyclerList.layoutManager = LinearLayoutManager(context)
        binding.rPostRcyclerList.setHasFixedSize(true)

        PostFirestore.instance.getAllPosts { posts ->
            this.posts = posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()
        }

        postsRecyclerView = binding.rPostRcyclerList
        postsRecyclerView?.setHasFixedSize(true)
        postsRecyclerView?.layoutManager = LinearLayoutManager(context)

        adapter = PostsRecyclerAdapter(posts)
        adapter?.listener = object : PostsRcyclerViewActivity.OnItemClickedListener {
            override fun OnItemClick(position: Int) {
                Log.i("TAG", "Position Clicked $position")
            }

            override fun onPostClicked(post: Post?) {
                Log.i("TAG", "Post $post")
                // Handle post click action here
            }
        }

        postsRecyclerView?.adapter = adapter

        return view
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}
