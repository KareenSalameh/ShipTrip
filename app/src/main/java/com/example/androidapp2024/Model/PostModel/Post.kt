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
    var itemWeight: String,
    var payForShipping: Boolean,
    var fromLocation: String,
    var toLocation: String,
    var isDeleted: Boolean = false,
    var itemDescription: String,
    val author: String, // You might want to add an author field
    val userId: String,
    val datePosted: Long = System.currentTimeMillis(),
  //  var lastUpdated: Long? = null
) {
  //  constructor() : this()

        constructor() : this(
        "", "", "", "", false, "", "", false,"", "", "", 0L, // Initialize lastUpdated to 0L
    )
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
        const val IS_DELETED_KEY = "is_deleted"


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
            val isDeleted = json[IS_DELETED_KEY] as? Boolean ?: false

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

            val timestamp = json[LAST_UPDATED] as? com.google.firebase.Timestamp
       //     post.lastUpdated = timestamp?.toDate()?.time ?: 0L

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
                IMAGE_URL to itemImageUri,
                ITEM_DESCRIPTION to itemDescription,
                PAYING_OR_NOT to payForShipping,
                POST_ID to postId,
                IS_DELETED_KEY to isDeleted,
                LAST_UPDATED to FieldValue.serverTimestamp()
            )
        }
    val deleteJson: Map<String, Any>
        get() {
            return hashMapOf(
                IS_DELETED_KEY to true,
                LAST_UPDATED to FieldValue.serverTimestamp(),
            )
        }

    val updateJson: Map<String, Any>
        get() {
            return hashMapOf(
                ITEM_NAME to itemName,
                USER_ID to userId,
                FROM_LOCATION to fromLocation,
                TO_LOCATION to toLocation,
                DATE_POSTED to datePosted,
                ITEM_WEIGHT to itemWeight,
                IMAGE_URL to itemImageUri,
                ITEM_DESCRIPTION to itemDescription,
                PAYING_OR_NOT to payForShipping,
                LAST_UPDATED to FieldValue.serverTimestamp(),
            )
        }

}
//package com.example.androidapp2024.Model.PostModel
//
//import android.content.Context
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.example.androidapp2024.base.MyApplication
//import com.google.firebase.Timestamp
//import com.google.firebase.firestore.FieldValue
//
//@Entity
//data class Post(
//    @PrimaryKey var postId: String,
//    var ItemName: String,
//    val itemImageUri: String , // Store the URI or path of the item image
//    val itemWeight: Double,
//    val payForShipping: Boolean,
//    val fromLocation: String ,
//    val toLocation: String ,
//    val itemDescription: String,
//    val author: String , // You might want to add an author field
//    val userId: String,
//    val datePosted: Long = System.currentTimeMillis(),
//    var lastUpdated: Long? = null) {
//
//
//    companion object {
//
//        var lastUpdated: Long
//            get() {
//                return MyApplication.Globals
//                    .appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
//                    ?.getLong(GET_LAST_UPDATED, 0) ?: 0
//            }
//            set(value) {
//                MyApplication.Globals
//                    ?.appContext
//                    ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)?.edit()
//                    ?.putLong(GET_LAST_UPDATED, value)?.apply()
//            }
//
//
//        const val POST_ID = "postId"
//        const val ITEM_NAME = "itemName"
//        const val ITEM_DESCRIPTION = "itemDescription"
//        const val IMAGE_URL =  "itemImageUri"
//        const val IMAGE_DEFAULT =
//            "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg"
//        const val ITEM_WEIGHT = "itemWeight"
//        const val PAYING_OR_NOT = "payForShipping"
//        const val FROM_LOCATION = "fromLocation"
//        const val TO_lOCATION = "toLocation"
//        const val LAST_UPDATED = "lastUpdated"
//        const val GET_LAST_UPDATED = "get_last_updated"
//        const val DATE_POSTED = "datePosted"
//        const val POST_AUTHOR = "author"
//        const val USER_ID = "userId"
//
//        fun fromJSON(json: Map<String, Any>): Post {
//            val Itemname = json[ITEM_NAME] as? String ?: ""
//            val userId = json[USER_ID] as? String ?: ""
//            val uri = json[IMAGE_URL] as? String ?: ""
//            val des = json[ITEM_DESCRIPTION] as? String ?: ""
//            val IsPaying = json[PAYING_OR_NOT] as? Boolean ?: false
//            val postUid = json[POST_ID] as? String ?: ""
//            val datePosted = json[DATE_POSTED] as? Long ?: 0
//            val weight = json[ITEM_WEIGHT] as? Double?: 0
//            val From = json[FROM_LOCATION] as? String?: ""
//            val To = json[TO_lOCATION] as? String?: ""
//            val userName = json[POST_AUTHOR] as? String?: ""
//
//
//
//            val post = Post(
//                ItemName = Itemname,
//                userId = userId,
//                itemImageUri = uri,
//                itemDescription = des,
//                payForShipping = IsPaying,
//                postId = postUid,
//                datePosted = datePosted,
//                itemWeight = weight,
//                fromLocation = From,
//                toLocation = To,
//                author = userName,
//
//            )
//            val timestamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
//            timestamp?.let {
//                post.lastUpdated = it.seconds
//            }
//            return post
//        }
//    }
//
//    val json: Map<String, Any>
//        get() {
//            return hashMapOf(
//                ItemName to Itemname,
//                USER_ID to userId,
//                DATE_POSTED to datePosted,
//                IMAGE_URL to uri,
//                ITEM_DESCRIPTION to description,
//                PAYING_OR_NOT to isChecked,
//                POST_ID to postUid,
//                LAST_UPDATED to FieldValue.serverTimestamp()
//            )
//        }
//}