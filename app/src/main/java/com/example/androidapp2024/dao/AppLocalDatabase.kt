package com.example.androidapp2024.dao

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.base.MyApplication
import com.example.greenapp.database.local.PostDao
import java.lang.IllegalStateException

@Database(entities = [User::class, Post::class], version = 15 )
abstract class AppLocalDbRepository: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun PostDao(): PostDao
}
object AppLocalDatabase {

    val db: AppLocalDbRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("App context not Available")

        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}