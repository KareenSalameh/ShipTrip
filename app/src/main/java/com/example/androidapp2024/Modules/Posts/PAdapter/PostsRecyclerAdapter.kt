package com.example.androidapp2024.Modules.Posts.PAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Modules.Posts.PostsRcyclerViewActivity
import com.example.androidapp2024.R

class PostsRecyclerAdapter(var posts: List<Post>?): RecyclerView.Adapter<PostViewHolder>(){

    var listener: PostsRcyclerViewActivity.OnItemClickedListener?= null

    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_post_row, parent,false)
        return PostViewHolder(itemView, listener, posts)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts?.get(position)
        holder.bind(post)
    }


}