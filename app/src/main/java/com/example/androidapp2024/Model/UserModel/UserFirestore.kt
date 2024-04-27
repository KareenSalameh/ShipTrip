package com.example.androidapp2024.Model.UserModel

import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import com.example.androidapp2024.dao.AppLocalDatabase
import java.util.concurrent.Executors

class UserFirestore private constructor() {
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = UserFirebaseModel()
    companion object {
        val instance: UserFirestore = UserFirestore()
    }

    interface GetAllUserListener{
        fun onComplete(users: List<User>)
    }
    fun getAllUsers(callback: (List<User>) -> Unit) {
        val lastUpdated:Long = User.lastUpdated

        firebaseModel.getAllStudents(lastUpdated){list ->
            Log.i("TAG", "FireBase returned ${list.size}, lastUpdated: $lastUpdated")

            executor.execute{
                var time = lastUpdated
                for(user in list){
                    database.userDao().insert(user)


                    user.lastUpdated?.let{
                        if(time < it){
                            time = user.lastUpdated ?:System.currentTimeMillis()
                        }
                    }
                    //update local data
                    User.lastUpdated = time
                    val users = database.userDao().getAll()

                    mainHandler.post{
                        callback(users)

                    }
                }
            }
        }

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

    }
}