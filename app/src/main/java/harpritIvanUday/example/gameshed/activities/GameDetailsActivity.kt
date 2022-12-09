package harpritIvanUday.example.gameshed.activities

import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameID = intent.getIntExtra("gameID", 0)
        Log.e("GameID", gameID.toString())
        getGamesData(gameID!!).start()
        addListenerOnRatingClick()
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