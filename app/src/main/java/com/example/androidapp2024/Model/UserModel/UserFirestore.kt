import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

import com.google.firebase.ktx.Firebase
class UserFirestore private constructor() {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection(UserFirebaseModel.USERS_COLLECTION_PATH)

    companion object {
        @Volatile
        private var INSTANCE: UserFirestore? = null

        fun getInstance(): UserFirestore {
            return INSTANCE ?: synchronized(this) {
                val instance = UserFirestore()
                INSTANCE = instance
                instance
            }
        }
    }

    fun getUserData(userId: String, onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {
        val userDocRef = usersCollection.document(userId)
        userDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userData = documentSnapshot.toObject(User::class.java)
                    onSuccess(userData!!)
                } else {
                    onFailure(Exception("User data not found"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun addUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userDocRef = usersCollection.document(user.userId)
        userDocRef.set(user, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}