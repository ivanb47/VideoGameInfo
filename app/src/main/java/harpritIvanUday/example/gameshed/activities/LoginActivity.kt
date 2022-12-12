package harpritIvanUday.example.gameshed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import harpritIvanUday.example.gameshed.databinding.ActivityLoginBinding
import harpritIvanUday.example.gameshed.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

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
        binding =ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val userObserver = Observer<FirebaseUser> { user ->
            // Update the UI, in this case, a TextView.
            if(user != null){
                moveToHome(user)

            }
        }
        viewModel.mutableUserLive.observe(this,userObserver)

        binding.buttonLogIn.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.emailTextLogin.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(binding.passwordText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = binding.emailTextLogin.text.toString().trim { it <= ' ' }
                    val password: String = binding.passwordText.text.toString().trim { it <= ' ' }
                    viewModel.firebaseLogin(email, password)
                }
            }

        }
        binding.goToSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}