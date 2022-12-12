package harpritIvanUday.example.gameshed.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.databinding.ActivitySignUpBinding
import harpritIvanUday.example.gameshed.viewModel.LoginViewModel


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: LoginViewModel

    private fun moveToHome(user: FirebaseUser) {
        val intent = Intent(this,HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id",user.uid)
        intent.putExtra("email_id",user.email)
        startActivity(intent)
        finish()
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = viewModel.firebaseCurrentUser()
        if(currentUser != null){
            moveToHome(currentUser)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.goToSignIn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val userObserver = Observer<FirebaseUser> { user ->
            // Update the UI, in this case, a TextView.
            if(user != null){
                moveToHome(user)
            }
        }
        viewModel.mutableUserLive.observe(this,userObserver)
        fun createUserData() {
            val userData = hashMapOf(
                "name" to binding.nameTextSignup.text.toString(),
                "email" to binding.emailTextLogin.text.toString(),
                "id" to viewModel.firebaseCurrentUser()?.uid
            )
            val db = Firebase.firestore
            db.collection("users").document(userData["id"].toString()).set(userData)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
        binding.signupButton.setOnClickListener{
            val email: String = binding.emailTextLogin.text.toString().trim {it <= ' '}
            val password: String = binding.passwordTextSignup.text.toString().trim {it <= ' '}
            val user = viewModel.firebaseSignup(email,password,this)
                    if (user != null) {
                        // Sign in success, update UI with the signed-in user's information
                        createUserData()
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(binding.nameTextSignup.text.toString())
                            .build()
                        user.updateProfile(profileUpdates)
                        moveToHome(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }


        }
    }
}