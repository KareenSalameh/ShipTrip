package com.example.androidapp2024.Modules.Posts.PAdapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Modules.Posts.PostsRcyclerViewActivity
import com.example.androidapp2024.R

class PostViewHolder(
    val itemView: View,
    val listener: PostsRcyclerViewActivity.OnItemClickedListener?,
    var posts: List<Post>?
) : RecyclerView.ViewHolder(itemView) {
    private var itemNameTextView: TextView? = null
    private var itemWeightTextView: TextView? = null
    private var fromLocationTextView: TextView? = null
    private var toLocationTextView: TextView? = null
    private var itemDescriptionTextView: TextView? = null
    private var payForShippingCheckBox: CheckBox? = null
    private var payOrNotText: TextView? = null
    private var itemImageView: ImageView? = null
    private var post: Post? = null

    init {
        itemNameTextView = itemView.findViewById(R.id.ItemName)
        itemWeightTextView = itemView.findViewById(R.id.WeightCount)
        fromLocationTextView = itemView.findViewById(R.id.tvfromLocation)
        toLocationTextView = itemView.findViewById(R.id.tvtoLocation)
        itemDescriptionTextView = itemView.findViewById(R.id.DescriptionItemText)
      //  payForShippingSwitch = itemView.findViewById(R.id.pa)
        payForShippingCheckBox = itemView.findViewById(R.id.checkBox)
        payOrNotText = itemView.findViewById(R.id.PayOrNotText)

        itemImageView = itemView.findViewById(R.id.UploadPhoto)
        payForShippingCheckBox?.setOnCheckedChangeListener { buttonView, isChecked ->
            updatePayOrNotText(isChecked)
        }
        itemView.setOnClickListener {
            Log.i("TAG", "PostViewHolder:Position clicked $adapterPosition")
            listener?.OnItemClick(adapterPosition)
            listener?.onPostClicked(post)
        }

    }
    private fun updatePayOrNotText(isChecked: Boolean) {
        payOrNotText?.setText(if (isChecked) "Yes" else "No")
    }
    fun bind(post: Post?) {
        this.post = post
        itemNameTextView?.text = post?.itemName
        itemWeightTextView?.text = post?.itemWeight.toString()
        fromLocationTextView?.text = post?.fromLocation
        toLocationTextView?.text = post?.toLocation
        itemDescriptionTextView?.text = post?.itemDescription
        payForShippingCheckBox?.isChecked = post?.payForShipping ?: false
        updatePayOrNotText(post?.payForShipping ?: false)
        if (!post?.itemImageUri.isNullOrEmpty()) {
            Glide.with(itemView)
                .load(post?.itemImageUri)
                .into(itemImageView!!)
        } else {
            itemImageView?.setImageResource(R.drawable.ship)
        }
        // You might need to set the itemImageView here
    }
}
//package com.example.androidapp2024.Modules.Posts.PAdapter
//
//import android.util.Log
//import android.view.View
//import android.widget.CheckBox
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.androidapp2024.Model.PostModel.Post
//import com.example.androidapp2024.Modules.Posts.PostsRcyclerViewActivity
//import com.example.androidapp2024.R
//
//class PostViewHolder(val itemView: View,
//                     val listener: PostsRcyclerViewActivity.OnItemClickedListener?,
//                     var posts: List<Post>?): RecyclerView.ViewHolder(itemView){
//
//
//    var nameTextView: TextView? = null
//    var idTextView: TextView? = null
//    var userCheckBox: CheckBox? = null
//    var post : Post? = null
//
//    init{
//        nameTextView = itemView.findViewById(R.id.tvUserName)
//        idTextView = itemView.findViewById(R.id.tvUserID)
//        userCheckBox = itemView.findViewById(R.id.cbUserListRow)
//
//        userCheckBox?.setOnClickListener{
//            var post = posts?.get(adapterPosition)
//            post?.isChecked = userCheckBox?.isChecked ?: false
//        }
//        itemView.setOnClickListener{
//            Log.i("TAG","PostViewHolder:Posotion clicked $adapterPosition")
//
//            listener?.OnItemClick(adapterPosition)
//            listener?.onPostClicked(post)
//        }
//    }
//
//    fun bind(post: Post?){
//        this.post = post
//        nameTextView?.text = post?.name
//        idTextView?.text = post?.postUid
//        userCheckBox?.apply {
//            isChecked = post?.isChecked?: false
//            //  tag = position
//        }
//    }
//
//}