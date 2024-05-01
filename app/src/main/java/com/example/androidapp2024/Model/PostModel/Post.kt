package com.example.androidapp2024.Model.PostModel

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidapp2024.base.MyApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

@Entity
data class Post(
    @PrimaryKey var postId: String,
    var itemName: String,
    val itemImageUri: String, // Store the URI or path of the item image
    val itemWeight: String,
    val payForShipping: Boolean,
    val fromLocation: String,
    val toLocation: String,
    val itemDescription: String,
    val author: String, // You might want to add an author field
    val userId: String,
    val datePosted: Long = System.currentTimeMillis(),
    var lastUpdated: Long? = null
) {
    companion object {
        var lastUpdated: Long
            get() {
                return MyApplication.Globals.appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.getLong(GET_LAST_UPDATED, 0) ?: 0
            }
            set(value) {
                MyApplication.Globals.appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)?.edit()
                    ?.putLong(GET_LAST_UPDATED, value)?.apply()
            }

        const val POST_ID = "postId"
        const val ITEM_NAME = "itemName"
        const val ITEM_DESCRIPTION = "itemDescription"
        const val IMAGE_URL = "itemImageUri"
        const val IMAGE_DEFAULT = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg"
        const val ITEM_WEIGHT = "itemWeight"
        const val PAYING_OR_NOT = "payForShipping"
        const val FROM_LOCATION = "fromLocation"
        const val TO_LOCATION = "toLocation"
        const val LAST_UPDATED = "lastUpdated"
        const val GET_LAST_UPDATED = "get_last_updated"
        const val DATE_POSTED = "datePosted"
        const val POST_AUTHOR = "author"
        const val USER_ID = "userId"

        fun fromJSON(json: Map<String, Any>): Post {
            val itemName = json[ITEM_NAME] as? String ?: ""
            val userId = json[USER_ID] as? String ?: ""
            val uri = json[IMAGE_URL] as? String ?: ""
            val des = json[ITEM_DESCRIPTION] as? String ?: ""
            val isPaying = json[PAYING_OR_NOT] as? Boolean ?: false
            val postUid = json[POST_ID] as? String ?: ""
            val datePosted = json[DATE_POSTED] as? Long ?: 0
            val weight = json[ITEM_WEIGHT] as? String ?: ""
            val from = json[FROM_LOCATION] as? String ?: ""
            val to = json[TO_LOCATION] as? String ?: ""
            val userName = json[POST_AUTHOR] as? String ?: ""
            val post = Post(
                itemName = itemName,
                userId = userId,
                itemImageUri = uri,
                itemDescription = des,
                payForShipping = isPaying,
                postId = postUid,
                datePosted = datePosted,
                itemWeight = weight,
                fromLocation = from,
                toLocation = to,
                author = userName
            )
            val timestamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
            timestamp?.let { post.lastUpdated = it.seconds }
            return post
        }
    }

    val json: Map<String, Any>
        get() {
            return hashMapOf(
                ITEM_NAME to itemName,
                USER_ID to userId,
                FROM_LOCATION to fromLocation,
                TO_LOCATION to toLocation,
                DATE_POSTED to datePosted,
                ITEM_WEIGHT to itemWeight,
                PAYING_OR_NOT to payForShipping,
                IMAGE_URL to itemImageUri,
                ITEM_DESCRIPTION to itemDescription,
                PAYING_OR_NOT to payForShipping,
                POST_ID to postId,
                LAST_UPDATED to FieldValue.serverTimestamp()
            )
        }
}