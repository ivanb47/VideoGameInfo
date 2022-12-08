package harpritIvanUday.example.gameshed.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import harpritIvanUday.example.gameshed.OnGameClickListener
import harpritIvanUday.example.gameshed.adapters.PopularRecyclerViewAdapter
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.activities.GameDetailsActivity
import harpritIvanUday.example.gameshed.activities.HomeActivity
import harpritIvanUday.example.gameshed.databinding.ActivityHomeBinding
import harpritIvanUday.example.gameshed.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class PopularGamesFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = PopularRecyclerViewAdapter(PlaceholderContent.ITEMS)

            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            PopularGamesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }


}