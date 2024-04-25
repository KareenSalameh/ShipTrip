package com.example.androidapp2024.Modules.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.Model
import com.example.androidapp2024.Model.User
import com.example.androidapp2024.Modules.User.Adapter.UsersRecyclerAdapter
import com.example.androidapp2024.R
import com.example.androidapp2024.databinding.ActivityUsersRcyclerViewBinding

class UsersRcyclerViewActivity : AppCompatActivity() {

    var usersRcyclerView: RecyclerView? = null
    var users: List<User>? = null
    var adapter: UsersRecyclerAdapter?= null

    private lateinit var binding: ActivityUsersRcyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsersRcyclerViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Model.instance.getAllUsers { users ->
            this.users=users
            adapter?.users = users
            adapter?.notifyDataSetChanged()
        }
//            (object: Model.GetAllUserListener {
//                override fun onComplete(users: List<User>) {
//                    TODO("Not yet implemented")
//                }
//            })


        usersRcyclerView = binding.rvUserRcyclerList//findViewById(R.id.rvUserRcyclerList)
        usersRcyclerView?.setHasFixedSize(true)
        usersRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = UsersRecyclerAdapter(users)
        adapter?.listener = object : OnItemClickedListener {
            override fun OnItemClick(position: Int) {
                Log.i("TAG", "Position CLicked $position")
            }

            override fun onUserClicked(user: User?) {
                Log.i("TAG", "USER $user")
            }

        }
        usersRcyclerView?.adapter = adapter
    }

    interface OnItemClickedListener {
        fun OnItemClick(position: Int)//user
        fun onUserClicked(user: User?)

    }

    override fun onResume() {
        super.onResume()
        Model.instance.getAllUsers { users ->
            this.users=users
            adapter?.users = users
            adapter?.notifyDataSetChanged()
        }
    }
}