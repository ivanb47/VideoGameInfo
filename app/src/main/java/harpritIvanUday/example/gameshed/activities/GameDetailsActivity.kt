package harpritIvanUday.example.gameshed.activities

import android.R.attr.button
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import harpritIvanUday.example.gameshed.R


class GameDetailsActivity : AppCompatActivity() {
    var ratingbar: RatingBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)
        addListenerOnRatingClick()
    }

    private fun addListenerOnRatingClick() {
        ratingbar = findViewById(R.id.ratingBar)
        //Performing action on Button Click
        //Performing action on Button Click
            val rating = ratingbar!!.rating.toString()
            Toast.makeText(applicationContext, rating, Toast.LENGTH_LONG).show()
    }
}