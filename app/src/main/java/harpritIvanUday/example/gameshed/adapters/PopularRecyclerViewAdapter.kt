package harpritIvanUday.example.gameshed.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import harpritIvanUday.example.gameshed.OnGameClickListener
import harpritIvanUday.example.gameshed.activities.GameDetailsActivity
import harpritIvanUday.example.gameshed.databinding.ActivityHomeBinding

import harpritIvanUday.example.gameshed.placeholder.PlaceholderContent.PlaceholderItem
import harpritIvanUday.example.gameshed.databinding.FragmentPopularBinding


class PopularRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
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
        holder.tvGame.text = item.id
       // holder.imgGame.text = item.content
        holder.cardGame.setOnClickListener{
          //  onGameClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvGame: TextView = binding.tvGameName
        val imgGame: ImageView = binding.imgGame
        val cardGame: CardView = binding.cardView


        override fun toString(): String {
            return super.toString() + " '" + tvGame.text + "'"
        }

    }

}