// AddPostFragment.kt
package com.example.androidapp2024.Modules.Posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.R

class AddPostFragment : Fragment() {
    private var descriptionEditText: EditText? = null
    private var fromLocationEditText: EditText? = null
    private var toLocationEditText: EditText? = null
    private var willToPayCheckBox: CheckBox? = null
    private var notToPayCheckBox: CheckBox? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        descriptionEditText = view.findViewById(R.id.editTextTextMultiLine)
        fromLocationEditText = view.findViewById(R.id.FromLocation)
        toLocationEditText = view.findViewById(R.id.ToLocation)
        willToPayCheckBox = view.findViewById(R.id.cbWilltoPay)
        notToPayCheckBox = view.findViewById(R.id.cbNotToPay)
        saveButton = view.findViewById(R.id.btnAddStudentSave)
        cancelButton = view.findViewById(R.id.btnAddStudentCancel)

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        saveButton?.setOnClickListener {
            val description = descriptionEditText?.text.toString()
            val fromLocation = fromLocationEditText?.text.toString()
            val toLocation = toLocationEditText?.text.toString()
            val willToPay = willToPayCheckBox?.isChecked ?: false
            val notToPay = notToPayCheckBox?.isChecked ?: false

            val post = Post(
                name = "",
                description = description,
                userId = "user_id_here",
                isChecked = false,
                postUid = "unique_post_id_here"
            )
            PostFirestore().addPost(post) {
                Navigation.findNavController(it).popBackStack()
            }
        }
    }
}
