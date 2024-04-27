package com.example.androidapp2024.Modules.User.Adapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Modules.User.UsersRcyclerViewActivity
import com.example.androidapp2024.R

class UserViewHolder(val itemView: View,
                     val listener: UsersRcyclerViewActivity.OnItemClickedListener?,
                     var users: List<User>?): RecyclerView.ViewHolder(itemView){


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

    fun bind(user: User?){
        this.user = user
        nameTextView?.text = user?.name
        idTextView?.text = user?.id
        userCheckBox?.apply {
            isChecked = user?.isChecked?: false
            //  tag = position
        }
    }

}