package harpritIvanUday.example.gameshed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import harpritIvanUday.example.gameshed.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
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
        val currentUser = auth.currentUser
        if(currentUser != null){
            moveToHome(currentUser)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        binding.buttonLogIn.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.emailTextLogin.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(binding.passwordText.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = binding.emailTextLogin.text.toString().trim {it <= ' '}
                    val password: String = binding.passwordText.text.toString().trim {it <= ' '}
                    auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this,"You are logged in successfully",Toast.LENGTH_SHORT).show()
                                    moveToHome(firebaseUser)
                                } else {
                                    Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                }
            }

        }
        binding.goToSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}