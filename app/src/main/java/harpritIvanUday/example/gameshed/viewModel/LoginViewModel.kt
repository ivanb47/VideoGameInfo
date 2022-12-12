package harpritIvanUday.example.gameshed.viewModel

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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

    fun createUserData(name: String, email: String, id:String) {
        val userData = hashMapOf(
            "name" to name,
            "email" to email,
            "id" to id
        )
        val db = Firebase.firestore
        db.collection("users").document(userData["id"].toString()).set(userData)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun firebaseSignup(email: String, password: String, name:String, activity: Activity): FirebaseUser? {
         auth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            createUserData(name, email, user!!.uid)
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        mutableUserLive.value = user!!
                                    }
                                }
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