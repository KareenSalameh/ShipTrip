package com.example.androidapp2024.Model

import android.media.audiofx.AudioEffect.Descriptor
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.androidapp2024.dao.AppLocalDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Model private constructor() {
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()
    companion object {
        val instance: Model = Model()
    }

    interface GetAllUserListener{
        fun onComplete(users: List<User>)
    }
    fun getAllUsers(callback: (List<User>) -> Unit) {
        firebaseModel.getAllStudents(callback)
//        executor.execute{
//
//            Thread.sleep(5000)
//
//            val users = database.userDao().getAll()
//            mainHandler.post{
//                // Main Thread
//                callback(users)
//            }
//        }
    }
    fun addUser(user: User, callback: () -> Unit){
        firebaseModel.addUser(user, callback)
//        executor.execute{
//            database.userDao().insert(user)
//            mainHandler.post{
//                callback()
//            }
//        }
    }
}