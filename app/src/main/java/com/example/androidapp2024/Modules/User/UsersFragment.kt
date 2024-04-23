package com.example.androidapp2024.Modules.User

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.Model
import com.example.androidapp2024.Model.User
import com.example.androidapp2024.Modules.User.Adapter.UsersRecyclerAdapter
import com.example.androidapp2024.R

class UsersFragment : Fragment() {
    var usersRcyclerView: RecyclerView? = null
    var users: MutableList<User>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_users, container, false)

        users = Model.instance.users
        usersRcyclerView = view.findViewById(R.id.UserFragmentList)
        usersRcyclerView?.setHasFixedSize(true)
        usersRcyclerView?.layoutManager = LinearLayoutManager(context)

        val adapter = UsersRecyclerAdapter(users)
        adapter.listener = object : UsersRcyclerViewActivity.OnItemClickedListener {
            override fun OnItemClick(position: Int) {
                Log.i("TAG", "Position CLicked $position")
            }

            override fun onUserClicked(user: User?) {
                Log.i("TAG", "USER $user")
            }

        }
        usersRcyclerView?.adapter = adapter

        return view
    }

}