package com.example.androidapp2024.Model.UserModel

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidapp2024.Model.PostModel.Post
import com.example.androidapp2024.base.MyApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

@Entity
data class User(
    @PrimaryKey val userId: String,
    val name: String,
    val userImgUrl: String,
    val email: String,
    val location:String,
    var lastUpdated: Long ?= null) {
    constructor() : this("", "null", "", "null@gmail.com", "", null)

    companion object{
        var lastUpdated: Long
            get(){
                return MyApplication.Globals
                    .appContext?.getSharedPreferences("TAG",Context.MODE_PRIVATE)
                    ?.getLong(GET_LAST_UPDATED,0) ?: 0
            }
            set(value){
                MyApplication.Globals
                    ?.appContext
                    ?.getSharedPreferences("TAG",Context.MODE_PRIVATE)?.edit()
                    ?.putLong(GET_LAST_UPDATED,value)?.apply()

            }
        const val ID_KEY = "userId"
        const val NAME_KEY = "name"
        const val USER_URL_KEY = "userImgUrl"
        const val EMAIL_KEY = "email"
        const val LOCATION_KEY = "location"
        const val LAST_UPDATED = "lastUpdated"
        const val GET_LAST_UPDATED = "get_last_updated"
        const val IMG_KEY = "userImgUrl"

        fun fromJSON(json: Map<String,Any>): User {
            val uri = json[Post.IMAGE_URL] as? String ?: ""
            val userId = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val userImgUrl = json[USER_URL_KEY] as? String ?: ""
            val email = json[EMAIL_KEY] as? String?: ""
            val location = json[LOCATION_KEY] as? String?: ""
            val u =  User(userId, name,userImgUrl, email, location)

            val timestamp: Timestamp?= json[LAST_UPDATED] as? Timestamp
            timestamp?.let{
                u.lastUpdated = it.seconds
            }
            return u
        }
    }

    val json: Map<String, Any>
        get(){
            return hashMapOf(
                IMG_KEY to userImgUrl,
                ID_KEY to userId,
                NAME_KEY to name,
                USER_URL_KEY to userImgUrl,
                EMAIL_KEY to email,
                LOCATION_KEY to location,
                LAST_UPDATED to FieldValue.serverTimestamp()
            )
        }
}