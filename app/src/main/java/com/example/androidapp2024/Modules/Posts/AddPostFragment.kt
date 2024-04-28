package com.example.androidapp2024.Modules.Posts

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.R
import com.google.firebase.firestore.FirebaseFirestore


class AddPostFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    val postId = db.collection("Posts").document().id
    val userId = db.collection("User").document().id

    //private val IMAGE_PICK_CODE = 1000
    //private var imageUri: Uri? = null
    private var itemImageView: ImageView? = null
    private var itemNameEditText: EditText? = null
    private var itemWeightEditText: EditText? = null
    private var payForShippingSwitch: Switch? = null
    private var fromLocationEditText: EditText? = null
    private var toLocationEditText: EditText? = null
    private var itemDescriptionEditText: EditText? = null
    private var addPostButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        itemImageView = view.findViewById(R.id.ivItemImage)
//        itemImageView?.setOnClickListener {
//            openImagePicker()
//        }
        itemNameEditText = view.findViewById(R.id.etItemName)
        itemWeightEditText = view.findViewById(R.id.etItemWeight)
      //  payForShippingSwitch = view.findViewById(R.id.swPayForShipping)
        fromLocationEditText = view.findViewById(R.id.etFromLocation)
        toLocationEditText = view.findViewById(R.id.etToLocation)
        itemDescriptionEditText = view.findViewById(R.id.etItemDescription)
        addPostButton = view.findViewById(R.id.btnAddPost)
        cancelButton = view.findViewById(R.id.button)

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        addPostButton?.setOnClickListener {
            val itemName = itemNameEditText?.text.toString()
            val itemWeight = itemWeightEditText?.text.toString()
            val payForShipping = payForShippingSwitch?.isChecked ?: false
            val fromLocation = fromLocationEditText?.text.toString()
            val toLocation = toLocationEditText?.text.toString()
            val itemDescription = itemDescriptionEditText?.text.toString()

            // You might need to handle the itemImageUri here

            val post = Post(
                itemName = itemName,
                itemImageUri = "", // Set the itemImageUri
                itemWeight = itemWeight,
                payForShipping = payForShipping,
                fromLocation = fromLocation,
                toLocation = toLocation,
                itemDescription = itemDescription,
                author = "", // Set the author
                userId = userId,
                postId = postId
            )

            PostFirestore().addPost(post) {
                Navigation.findNavController(it).popBackStack()
            }
        }
    }

}
//// AddPostFragment.kt
//package com.example.androidapp2024.Modules.Posts
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.CheckBox
//import androidx.fragment.app.Fragment
//import androidx.navigation.Navigation
//import com.example.androidapp2024.Model.PostModel.Post
//import com.example.androidapp2024.Model.PostModel.PostFirestore
//import com.example.androidapp2024.R
//
//class AddPostFragment : Fragment() {
//    private var descriptionEditText: EditText? = null
//    private var fromLocationEditText: EditText? = null
//    private var toLocationEditText: EditText? = null
//    private var willToPayCheckBox: CheckBox? = null
//    private var notToPayCheckBox: CheckBox? = null
//    private var saveButton: Button? = null
//    private var cancelButton: Button? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
//        setupUI(view)
//        return view
//    }
//
//    private fun setupUI(view: View) {
//        descriptionEditText = view.findViewById(R.id.ed)
//        fromLocationEditText = view.findViewById(R.id.FromLocation)
//        toLocationEditText = view.findViewById(R.id.ToLocation)
//        willToPayCheckBox = view.findViewById(R.id.cbWilltoPay)
//        notToPayCheckBox = view.findViewById(R.id.cbNotToPay)
//        saveButton = view.findViewById(R.id.btnAddStudentSave)
//        cancelButton = view.findViewById(R.id.btnAddStudentCancel)
//
//        cancelButton?.setOnClickListener {
//            Navigation.findNavController(it).popBackStack()
//        }
//
//        saveButton?.setOnClickListener {
//            val description = descriptionEditText?.text.toString()
//            val fromLocation = fromLocationEditText?.text.toString()
//            val toLocation = toLocationEditText?.text.toString()
//            val willToPay = willToPayCheckBox?.isChecked ?: false
//            val notToPay = notToPayCheckBox?.isChecked ?: false
//
//            val post = Post(
//                name = "",
//                description = description,
//                userId = "user_id_here",
//                isChecked = false,
//                postUid = "unique_post_id_here"
//            )
//            PostFirestore().addPost(post) {
//                Navigation.findNavController(it).popBackStack()
//            }
//        }
//    }
//}
