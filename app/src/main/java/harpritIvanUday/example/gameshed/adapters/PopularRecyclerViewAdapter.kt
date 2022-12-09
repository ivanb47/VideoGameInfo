package harpritIvanUday.example.gameshed.adapters

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.common.collect.ComparisonChain.start
import com.squareup.picasso.Picasso
import harpritIvanUday.example.gameshed.OnGameClickListener
import harpritIvanUday.example.gameshed.Results
import harpritIvanUday.example.gameshed.activities.GameDetailsActivity
import harpritIvanUday.example.gameshed.activities.HomeActivity
import harpritIvanUday.example.gameshed.databinding.ActivityHomeBinding

import harpritIvanUday.example.gameshed.placeholder.PlaceholderContent.PlaceholderItem
import harpritIvanUday.example.gameshed.databinding.FragmentPopularBinding
import kotlinx.coroutines.NonCancellable.start
import kotlin.math.log


class PopularRecyclerViewAdapter(
    private val values: List<Results>
) : RecyclerView.Adapter<PopularRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.tvGame.text = item.name
        holder.tvGameRating.text = "Rating: " + item.rating.toString()
        holder.tvGameRelease.text = "Release Date: " +item.released
        Picasso.get().load(item.background_image).into(holder.imgGame)
        holder.cardGame.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Clicked", Toast.LENGTH_SHORT).show()
            Intent (holder.itemView.context, GameDetailsActivity::class.java).also {
                Log.e("Game Name", item.id.toString())
                it.putExtra("gameID", item.id)
                holder.itemView.context.startActivity(it)
            }
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvGame: TextView = binding.tvGameName
        val tvGameRating: TextView = binding.tvGameRating
        val tvGameRelease: TextView = binding.tvGameRelease
        val imgGame: ImageView = binding.imgGame
        val cardGame: CardView = binding.cardView


        override fun toString(): String {
            return super.toString() + " '" + tvGame.text + "'"
        }

    }

}