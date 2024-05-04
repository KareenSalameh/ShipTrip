package com.example.androidapp2024.Modules.Posts.PAdapter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Modules.Posts.PostsRcyclerViewActivity
import com.example.androidapp2024.R
import androidx.navigation.fragment.findNavController

import com.google.android.material.floatingactionbutton.FloatingActionButton

class PostViewHolder(
    val itemView: View,
    var listener: PostsRcyclerViewActivity.OnItemClickedListener?,
    var posts: List<Post>?,
    isMyPostsFragment: Boolean
) : RecyclerView.ViewHolder(itemView) {
    private var itemNameTextView: TextView? = null
    private var itemWeightTextView: TextView? = null
    private var fromLocationTextView: TextView? = null
    private var toLocationTextView: TextView? = null
    private var itemDescriptionTextView: TextView? = null
    private var payForShippingCheckBox: CheckBox? = null
    private var payOrNotText: TextView? = null
    private var itemImageView: ImageView? = null
    private var postContentTextView: TextView? = null
    private var editPostImageView: ImageView? = null
    private var deletePostImageView: ImageView? = null
    private var post: Post? = null
    private var floatingActionButton: FloatingActionButton? = null
    private var ClickToShipTextView : TextView?= null


    init {
        itemNameTextView = itemView.findViewById(R.id.ItemName)
        itemWeightTextView = itemView.findViewById(R.id.WeightCount)
        fromLocationTextView = itemView.findViewById(R.id.tvfromLocation)
        toLocationTextView = itemView.findViewById(R.id.tvtoLocation)
        itemDescriptionTextView = itemView.findViewById(R.id.DescriptionItemText)
        //  payForShippingSwitch = itemView.findViewById(R.id.pa)
        //  payForShippingCheckBox = itemView.findViewById(R.id.checkBox)
        payOrNotText = itemView.findViewById(R.id.PayOrNotText)
        floatingActionButton = itemView.findViewById(R.id.floatingActionButton5)
        editPostImageView = itemView.findViewById(R.id.iconEdit)
        deletePostImageView = itemView.findViewById(R.id.icondelete)
        ClickToShipTextView = itemView.findViewById(R.id.tvClickToShip)
        itemImageView = itemView.findViewById(R.id.UploadPhoto)
        payForShippingCheckBox?.setOnCheckedChangeListener { buttonView, isChecked ->
            updatePayOrNotText(isChecked)
        }
        itemView.setOnClickListener {
            Log.i("TAG", "PostViewHolder:Position clicked $adapterPosition")
            listener?.OnItemClick(adapterPosition)
            listener?.onPostClicked(post)
        }
        if (isMyPostsFragment) {
            floatingActionButton?.visibility = View.GONE
            ClickToShipTextView?.visibility = View.GONE
            editPostImageView?.visibility = View.VISIBLE
            deletePostImageView?.visibility = View.VISIBLE
        } else {
            floatingActionButton?.visibility = View.VISIBLE
            ClickToShipTextView?.visibility = View.VISIBLE
            editPostImageView?.visibility = View.GONE
            deletePostImageView?.visibility = View.GONE
        }
        editPostImageView?.setOnClickListener {
          //  navigateToEditPost()
            findNavController(itemView).navigate(R.id.action_global_editPostFragment)

        }
        floatingActionButton?.setOnClickListener {
            val bundle = Bundle().apply {
                putString("post", post?.postId)
            }
            findNavController(itemView).navigate(R.id.action_postsFragment_to_firstFragment, bundle)
        }

        deletePostImageView?.setOnClickListener {
            listener?.onDeletePostClicked(post)
        }
    }
    private fun navigateToEditPost() {

    }

    private fun updatePayOrNotText(isChecked: Boolean) {
        payOrNotText?.setText(if (isChecked) "Yes" else "No")
    }
    fun bind(post: Post?) {
        this.post = post
        postContentTextView?.text = post?.itemDescription
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
    }
}