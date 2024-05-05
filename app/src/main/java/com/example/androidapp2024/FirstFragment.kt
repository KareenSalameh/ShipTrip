import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.androidapp2024.Model.PostModel.PostFirestore
import com.example.androidapp2024.Model.UserModel.User
import com.example.androidapp2024.Model.UserModel.UserFirebaseModel
import com.example.androidapp2024.R
import de.hdodenhof.circleimageview.CircleImageView

class FirstFragment : Fragment() {
    private lateinit var profileImageView: CircleImageView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private val userFirestore = UserFirestore.getInstance()
    private var postId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        profileImageView = view.findViewById(R.id.profileImage3)
        tvName = view.findViewById(R.id.tvNameOfUser3)
        tvEmail = view.findViewById(R.id.tvEmailOfUser3)
//
        val postId = arguments?.getString("postId")
        if (postId != null) {
            fetchPostData(postId)
        }

        return view
    }
    private fun fetchPostData(postId: String) {
        PostFirestore.getInstance().getPostData(postId,
            onSuccess = { post ->
                fetchUserData(post.userId)
            },
            onFailure = { exception ->
                // Handle the failure case
                Log.i("TAG", "Failing to fetch Post Data: ${exception.message}")
            }
        )
    }
    private fun fetchUserData(userId: String) {
        userFirestore.getUserData(userId,
            onSuccess = { user ->
                displayUserData(user)
            },
            onFailure = { exception ->
                Log.e("TAG", "Failed to fetch user data", exception)
            }
        )
    }

    private fun displayUserData(user: User) {
        tvName.text = user.name
        tvEmail.text = user.email

        // Load profile image using Glide or any other image loading library
        // Example using Glide:
        Glide.with(requireContext())
            .load(user.userImgUrl)
            .placeholder(R.drawable.baseline_account_circle_24)
            .error(R.drawable.baseline_account_circle_24)
            .into(profileImageView!!)
    }

//    private fun fetchPostData(postId: String) {
//        PostFirestore.getInstance().getPostData(postId,
//            onSuccess = { post ->
//                Log.e("FirstFragment", "Retreieved posts ${post.userId}")
//
//                fetchUserData(post.userId)
//            },
//            onFailure = { exception ->
//                // Handle the failure case
//                Log.i("TAG", "Failing to fetch Post Data: ${exception.message}")
//            }
//        )
//    }
//
//    private fun fetchUserData(userId: String) {
//        userFirestore.getUserData(userId,
//            onSuccess = { user ->
//                displayUserData(user)
//            },
//            onFailure = { exception ->
//                Log.e("TAG", "Failed to fetch user data", exception)
//            }
//        )
//    }

//    private fun displayUserData(user: User) {
//        tvName.text = user.name
//        tvEmail.text = user.email
//
//        // Load profile image using Glide or any other image loading library
//        // Example using Glide:
//        Glide.with(requireContext())
//            .load(user.userImgUrl)
//            .placeholder(R.drawable.baseline_account_circle_24)
//            .error(R.drawable.baseline_account_circle_24)
//            .into(profileImageView!!)
//    }
}
