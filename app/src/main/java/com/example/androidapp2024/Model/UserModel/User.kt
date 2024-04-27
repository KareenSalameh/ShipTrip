package com.example.androidapp2024.Model.UserModel

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidapp2024.base.MyApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

@Entity
data class User(
    @PrimaryKey val name: String,
    val id: String,
    val avatarUrl: String,
    var isChecked: Boolean,
    var lastUpdated: Long ?= null) {

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

        const val NAME_KEY = "name"
        const val ID_KEY = "id"
        const val AVATAR_URL_KEY = "avatarUrl"
        const val ISCHECKED_KEY = "isChecked"
        const val LAST_UPDATED = "lastUpdated"
        const val GET_LAST_UPDATED = "get_last_updated"
        fun fromJSON(json: Map<String,Any>): User {
            val id = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val avatarUrl = json[AVATAR_URL_KEY] as? String ?: ""
            val isChecked = json[ISCHECKED_KEY] as? Boolean ?: false
            val u =  User(name, id, avatarUrl, isChecked)

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
                ID_KEY to id,
                NAME_KEY to name,
                AVATAR_URL_KEY to avatarUrl,
                ISCHECKED_KEY to isChecked,
                LAST_UPDATED to FieldValue.serverTimestamp()
            )
        }
}