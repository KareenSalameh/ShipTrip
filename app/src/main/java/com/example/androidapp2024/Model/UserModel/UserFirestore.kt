import com.example.androidapp2024.Model.PostModel.PostFirebaseModel
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

import com.google.firebase.ktx.Firebase
class UserFirestore private constructor() {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection(UserFirebaseModel.USERS_COLLECTION_PATH)
    private val firebaseModel = UserFirebaseModel()

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
        firebaseModel.getUserData(userId,onSuccess,onFailure)
    }


    fun addUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseModel.addUser(user,onSuccess,onFailure)
    }
    fun updateUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseModel.updateUser(user,onSuccess,onFailure)
    }
}