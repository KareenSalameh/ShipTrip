package com.example.greenapp.database.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidapp2024.Model.PostModel.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM Post")
    fun getAll(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg posts: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Post>)

    @Query("SELECT * from post where userId = :userID")
    suspend fun getUserPosts(userID: String): List<Post>

    @Delete
    fun delete(post: Post)

    @Query("SELECT * FROM Post WHERE postId =:postUid")
    fun getPostById(postUid: String): List<Post>
}