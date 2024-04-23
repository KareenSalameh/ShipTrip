package com.example.androidapp2024.Modules.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import com.example.androidapp2024.Model.Model
import com.example.androidapp2024.Model.User
import com.example.androidapp2024.R

class ListActivity : AppCompatActivity() {

    var usersListView: ListView? = null
    var users: MutableList<User>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        users = Model.instance.users

        usersListView = findViewById(R.id.UserListView)
        usersListView?.adapter = UsersListAdapter(users)

        usersListView?.setOnItemClickListener { parent, view, position, id ->
            Log.i("TAG", "Row was clicked at $position")
        }
    }

    class UsersListAdapter(val users: MutableList<User>?): BaseAdapter() {
        override fun getCount(): Int = users?.size ?: 0

        override fun getItem(p0: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val user = users?.get(position)
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.activity_user_row, parent, false)
                val userCheckBox: CheckBox? = view?.findViewById(R.id.cbUserListRow)
                userCheckBox?.setOnClickListener{
                    (userCheckBox?.tag as? Int)?.let { tag ->
                        var user = users?.get(tag)
                        user?.isChecked = userCheckBox?.isChecked ?: false
                    }
                }
            }

            val nameTextView: TextView? = view?.findViewById(R.id.tvUserName)
            val idTextView: TextView? = view?.findViewById(R.id.tvUserID)
            val userCheckBox: CheckBox? = view?.findViewById(R.id.cbUserListRow)
            val postdescription: TextView? = view?.findViewById(R.id.svUserPostDescription)
            nameTextView?.text = user?.name
            idTextView?.text = user?.id
            postdescription?.text = user?.description
            userCheckBox?.apply {
                isChecked = user?.isChecked ?: false
                tag = position
            }

            return view!!
        }


        override fun getItemId(p0: Int): Long = 0
    }
}