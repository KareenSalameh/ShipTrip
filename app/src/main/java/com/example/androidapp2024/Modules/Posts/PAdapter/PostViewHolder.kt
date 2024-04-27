package com.example.androidapp2024.Modules.Posts.PAdapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Modules.Posts.PostsRcyclerViewActivity
import com.example.androidapp2024.R

class PostViewHolder(val itemView: View,
                     val listener: PostsRcyclerViewActivity.OnItemClickedListener?,
                     var posts: List<Post>?): RecyclerView.ViewHolder(itemView){


    var nameTextView: TextView? = null
    var idTextView: TextView? = null
    var userCheckBox: CheckBox? = null
    var post : Post? = null

    init{
        nameTextView = itemView.findViewById(R.id.tvUserName)
        idTextView = itemView.findViewById(R.id.tvUserID)
        userCheckBox = itemView.findViewById(R.id.cbUserListRow)

        userCheckBox?.setOnClickListener{
            var post = posts?.get(adapterPosition)
            post?.isChecked = userCheckBox?.isChecked ?: false
        }
        itemView.setOnClickListener{
            Log.i("TAG","PostViewHolder:Posotion clicked $adapterPosition")

            listener?.OnItemClick(adapterPosition)
            listener?.onPostClicked(post)
        }
    }

    fun bind(post: Post?){
        this.post = post
        nameTextView?.text = post?.name
        idTextView?.text = post?.postUid
        userCheckBox?.apply {
            isChecked = post?.isChecked?: false
            //  tag = position
        }
    }

}