package com.example.androidapp2024.Modules.User.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.User
import com.example.androidapp2024.Modules.User.UsersRcyclerViewActivity
import com.example.androidapp2024.R

class UsersRecyclerAdapter(var users: MutableList<User>?): RecyclerView.Adapter<UserViewHolder>(){

    var listener: UsersRcyclerViewActivity.OnItemClickedListener?= null
    override fun getItemCount(): Int = users?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_row, parent,false)
        return UserViewHolder(itemView, listener, users)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users?.get(position)
        holder.bind(user)
    }


}