package com.example.androidapp2024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddUserActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        setupUI()
    }

    private fun setupUI() {
        nameEditText = findViewById(R.id.editTextFullName)
        idEditText = findViewById(R.id.editTextEmail) // Assuming ID is the email here
        saveButton = findViewById(R.id.buttonSave)
        cancelButton = findViewById(R.id.buttonCancel)

        cancelButton.setOnClickListener {
            finish()
        }
        saveButton.setOnClickListener {
            // Here you can retrieve the values entered by the user
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            // You can then use these values as needed, such as saving to a database
        }
    }
}
