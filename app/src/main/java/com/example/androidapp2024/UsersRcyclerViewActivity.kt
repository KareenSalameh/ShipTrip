package com.example.androidapp2024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.Model
import com.example.androidapp2024.Model.User

class UsersRcyclerViewActivity : AppCompatActivity() {

    var usersRcyclerView: RecyclerView? = null
    var users: MutableList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_rcycler_view)

        users = Model.instance.users

        usersRcyclerView = findViewById(R.id.rvUserRcyclerList)
        usersRcyclerView?.setHasFixedSize(true)

        // Set the layout manager
        usersRcyclerView?.layoutManager = LinearLayoutManager(this)
        // Set the adapter
       // usersRcyclerView?.adapter = UsersRecyclerAdapter()

        val adapter = UsersRecyclerAdapter()
        adapter.listener = object : OnItemClickedListener{
            override fun OnItemClick(position: Int) {
                Log.i("TAG","Position CLicked $position")
            }

            override fun onUserClicked(user: User?) {
                Log.i("TAG","USER $user")
            }

        }
        usersRcyclerView?.adapter = adapter
    }

    interface OnItemClickedListener {
        fun OnItemClick(position: Int)//user
        fun onUserClicked(user:User?)

    }
    inner class UserViewHolder(val itemView: View, listener: OnItemClickedListener?): RecyclerView.ViewHolder(itemView){


        var nameTextView: TextView? = null
        var idTextView: TextView? = null
        var userCheckBox: CheckBox? = null
        var user : User? = null

        init{
            nameTextView = itemView.findViewById(R.id.tvUserName)
            idTextView = itemView.findViewById(R.id.tvUserID)
            userCheckBox = itemView.findViewById(R.id.cbUserListRow)

            userCheckBox?.setOnClickListener{
                var user = users?.get(adapterPosition)
                user?.isChecked = userCheckBox?.isChecked ?: false
            }
            itemView.setOnClickListener{
                Log.i("TAG","UserViewHolder:Posotion clicked $adapterPosition")

                listener?.OnItemClick(adapterPosition)
                listener?.onUserClicked(user)
            }
        }

        fun bind(user:User?){
            this.user = user
            nameTextView?.text = user?.name
            idTextView?.text = user?.id
            userCheckBox?.apply {
                isChecked = user?.isChecked?: false
              //  tag = position
            }
        }

    }
    inner class UsersRecyclerAdapter: RecyclerView.Adapter<UserViewHolder>(){

        var listener:OnItemClickedListener?= null
        override fun getItemCount(): Int = users?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_user_row, parent,false)
            return UserViewHolder(itemView, listener)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = users?.get(position)
            holder.bind(user)
        }

    }
}