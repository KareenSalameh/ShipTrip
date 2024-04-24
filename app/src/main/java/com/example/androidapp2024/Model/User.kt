package com.example.androidapp2024.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val name: String,
    val id: String,
    val avatarUrl: String,
    var isChecked: Boolean) {
}