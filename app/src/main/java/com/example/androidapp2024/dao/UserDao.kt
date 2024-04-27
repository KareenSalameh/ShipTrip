package com.example.androidapp2024.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidapp2024.Model.UserModel.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User WHERE id =:id")
    fun getUserById(id: String): User
}