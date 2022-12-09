package harpritIvanUday.example.gameshed.activities

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import harpritIvanUday.example.gameshed.GameDetail
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.databinding.ActivityGameDetailsBinding
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class GameDetailsActivity : AppCompatActivity() {
    var ratingbar: RatingBar? = null
    lateinit var binding: ActivityGameDetailsBinding
    private lateinit var userData: HashMap<String, Any>
    val currentUser = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().uid.toString())
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameID = intent.getIntExtra("gameID", 0)
        Log.e("GameID", gameID.toString())
        getGamesData(gameID!!).start()
        addListenerOnRatingClick()
        fetchUserData(gameID)
        binding.saveButton.setOnClickListener {
          // if userData.favorite contains gameID, remove it
            if( userData["favorites"].toString().contains(gameID.toString())){
                removeFromFavorite(gameID)
                Log.e("Removed from favorites", gameID.toString())
            }else{
                Log.e("Added to favorites", gameID.toString())
                addToFavorite(gameID)
            }
        }
    }
    private fun addToFavorite(gameID: Int) {
        currentUser
            .update("favorites", FieldValue.arrayUnion(gameID))
            .addOnSuccessListener {
                fetchUserData(gameID)
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }
    private fun removeFromFavorite(gameID: Int) {
        currentUser
            .update("favorites", FieldValue.arrayRemove(gameID))
            .addOnSuccessListener {
                fetchUserData(gameID)
                Log.d(TAG, "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }
    private fun fetchUserData(gameID: Int) {
        Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().uid.toString()).get().addOnSuccessListener {
            userData = it.data as HashMap<String, Any>
            if(userData["favorites"].toString().contains(gameID.toString())){
                binding.saveButton.text = "Saved"
            }else{
                binding.saveButton.text = "Click to save"
            }
            Log.e(TAG, "fetchUserData: $userData")
        }
    }
    private fun addListenerOnRatingClick() {
        ratingbar = findViewById(R.id.ratingBar)
        //Performing action on Button Click
        //Performing action on Button Click
            val rating = ratingbar!!.rating.toString()
    }
    private fun getGamesData(id: Int): Thread
    {
        Log.e("GameID", id.toString())
        return Thread {
            val uri = "https://api.rawg.io/api/games/"
            val key = "d64de3cb496f46a1a5b5f3b1669764e9"
            val url = URL("$uri$id?key=$key")
            val connection  = url.openConnection() as HttpsURLConnection

            Log.e("API",connection.inputStream.toString())
            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, GameDetail::class.java)
                Log.d("API", request.toString())
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                Log.e("API", "Error")
            }
        }
    }
    private fun updateUI( request: GameDetail){
        Log.e("Running on ui thread", request.toString())
        runOnUiThread{
            kotlin.run {
                binding.tvGameDetails.text = request.description_raw
                binding.topAppBar.title = request.name
                binding.ratingBar.rating = request.rating.toFloat()
                binding.tvRating.text = request.rating
                binding.tvReleaseDate.text = getString(R.string.release_date) + request.released
                binding.tvPlatform.text = getString(R.string.website) + request.website
                Picasso.get().load(request.background_image).into(binding.imgGame)
            }
        }
    }
}