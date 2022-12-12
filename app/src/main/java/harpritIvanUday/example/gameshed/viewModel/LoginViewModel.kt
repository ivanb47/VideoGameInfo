package harpritIvanUday.example.gameshed.viewModel

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel: ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    private var status: String ?= null
    var mutableUserLive = MutableLiveData<FirebaseUser>()

    fun firebaseLogin(email:String, password:String): FirebaseUser? {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mutableUserLive.value = task.result!!.user!!
                    status = "success"

                } else {
                    status = task.exception!!.message.toString()
                }
            }
        return mutableUserLive.value
    }

    fun firebaseCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun firebaseSignup(email: String, password: String, activity: Activity): FirebaseUser? {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)

                }
            }
        //Delay because it returns too fast
         runBlocking {
            launch {
                delay(1000L)

            }

        }
        return auth.currentUser
    }

}