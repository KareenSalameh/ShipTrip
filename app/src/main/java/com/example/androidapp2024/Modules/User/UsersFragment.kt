package com.example.androidapp2024.Modules.User

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.Model
import com.example.androidapp2024.Model.User
import com.example.androidapp2024.Modules.User.Adapter.UsersRecyclerAdapter
import com.example.androidapp2024.R

class UsersFragment : Fragment() {
    var usersRcyclerView: RecyclerView? = null
    var users: List<User>? = null
    var adapter: UsersRecyclerAdapter?= null
    var progressBar: ProgressBar?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_users, container, false)
        progressBar = view.findViewById(R.id.progressBar)

        progressBar?.visibility = View.VISIBLE

        Model.instance.getAllUsers { users ->
            this.users = users
            adapter?.users = users
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE

        }
        usersRcyclerView = view.findViewById(R.id.UserFragmentList)
        usersRcyclerView?.setHasFixedSize(true)
        usersRcyclerView?.layoutManager = LinearLayoutManager(context)

        adapter = UsersRecyclerAdapter(users)
        adapter?.listener = object : UsersRcyclerViewActivity.OnItemClickedListener {
            override fun OnItemClick(position: Int) {
                Log.i("TAG", "Position CLicked $position")
                val user = users?.get(position)
                user?.let {
                    val action = UsersFragmentDirections.actionUsersFragmentToFirstFragment(it.name)
                    Navigation.findNavController(view).navigate(action)

                }
            }

            override fun onUserClicked(user: User?) {
                Log.i("TAG", "USER $user")
            }

        }
        usersRcyclerView?.adapter = adapter

        val addUserButton: ImageButton = view.findViewById(R.id.ibtnAddUser)
        //val action = UsersFragmentDirections.actionGlobalAddUserFragment4()
        val action = Navigation.createNavigateOnClickListener(UsersFragmentDirections.actionGlobalAddUserFragment4())

        addUserButton.setOnClickListener(action)

        return view
    }

    override fun onResume() {
        super.onResume()

        progressBar?.visibility = View.VISIBLE

        Model.instance.getAllUsers { users ->
            this.users = users
            adapter?.users = users
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE

        }
    }

}