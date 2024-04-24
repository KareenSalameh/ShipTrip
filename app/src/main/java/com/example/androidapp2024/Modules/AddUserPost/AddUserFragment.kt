package com.example.androidapp2024.Modules.AddUserPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.androidapp2024.Model.Model
import com.example.androidapp2024.Model.User
import com.example.androidapp2024.R
import android.view.Menu
import android.view.MenuInflater

import androidx.navigation.Navigation


class AddUserFragment : Fragment() {
    private var nameTextField: EditText? = null
    private var idTextField: EditText? = null
    private var messageTextView: TextView? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_user, container, false)
        setupUI(view)
        return view
    }
    private fun setupUI(view: View) {
        nameTextField = view.findViewById(R.id.etAddStudentName)
        idTextField = view.findViewById(R.id.etAddStudentID)
        messageTextView = view.findViewById(R.id.tvAddStudentSaved)
        saveButton = view.findViewById(R.id.btnAddStudentSave)
        cancelButton = view.findViewById(R.id.btnAddStudentCancel)
        messageTextView?.text = ""

        cancelButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack(R.id.usersFragment, false)
        }
        saveButton?.setOnClickListener {
            val name = nameTextField?.text.toString()
            val id = idTextField?.text.toString()
            val user = User(name, id, "", false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}



//package com.example.androidapp2024.Modules.AddUserPost
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import com.example.androidapp2024.R
//
//
//class AddUserFragment : Fragment() {
//    private var nameEditText: EditText?= null
//    private var titlePost: EditText?= null
//    private var fromText: EditText?= null
//    private var camerafa: EditText?= null
//    private var description: EditText?= null
//    private var toText: EditText?= null
//    private var uploadPhotoText: EditText?= null
//    private var idEditText: EditText?= null
//    private var saveButton: Button?= null
//    private var cancelButton: Button?= null
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_add_user, container, false)
//        setupUI(view)
//        return view
//    }
//    private fun setupUI(view: View) {
//        nameEditText = view.findViewById(R.id.editTextFullName)
//        idEditText = view.findViewById(R.id.editTextEmail) // Assuming ID is the email here
//        saveButton = view.findViewById(R.id.buttonSave)
//        cancelButton = view.findViewById(R.id.buttonCancel)
//        titlePost = view.findViewById(R.id.tvTitlePost)
//        fromText = view.findViewById(R.id.tvFROM)
//        toText = view.findViewById(R.id.tvTextTO)
//        uploadPhotoText = view.findViewById(R.id.tvPhotoUpload)
//        camerafa = view.findViewById(R.id.faCameraButton)
//        description = view.findViewById(R.id.tvDescription)
//
//        cancelButton?.setOnClickListener {
//            //finish()
//            //TODO - handle later
//        }
//        saveButton?.setOnClickListener {
//            // Here you can retrieve the values entered by the user
//            val name = nameEditText?.text.toString()
//            val id = idEditText?.text.toString()
//            // You can then use these values as needed, such as saving to a database
//        }
//    }
//}