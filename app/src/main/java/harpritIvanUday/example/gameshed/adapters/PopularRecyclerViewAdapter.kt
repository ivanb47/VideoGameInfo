package harpritIvanUday.example.gameshed.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import harpritIvanUday.example.gameshed.Results
import harpritIvanUday.example.gameshed.activities.GameDetailsActivity
import harpritIvanUday.example.gameshed.databinding.FragmentPopularBinding


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