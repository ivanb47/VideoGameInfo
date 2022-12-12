package harpritIvanUday.example.gameshed.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.Results
import harpritIvanUday.example.gameshed.adapters.FavoriteRecyclerViewAdapter
import harpritIvanUday.example.gameshed.viewModel.HomeViewModel

/**
 * A fragment representing a list of Items.
 */
class FavouriteFragment : Fragment() {
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private var columnCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onResume() {
        val view = view
        super.onResume()
        sharedViewModel.getFavouriteGames()
        val famousObserver = Observer<List<Results>> {
            reloadList(view!!)
        }
        sharedViewModel.favoriteGamesLive.observe(viewLifecycleOwner, famousObserver)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_list, container, false)
    }
    private fun reloadList(view: View){
        Log.e("PopularGamesFragment", "onCreateView: ${sharedViewModel.favoriteGamesLive.value}" )
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = FavoriteRecyclerViewAdapter(sharedViewModel.favoriteGames)
            }
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

    }


}