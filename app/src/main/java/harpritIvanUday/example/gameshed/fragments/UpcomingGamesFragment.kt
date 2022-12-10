package harpritIvanUday.example.gameshed.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.activities.HomeActivity
import harpritIvanUday.example.gameshed.adapters.PopularRecyclerViewAdapter
import harpritIvanUday.example.gameshed.adapters.UpcomingGamesRecyclerViewAdapter
import harpritIvanUday.example.gameshed.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class UpcomingGamesFragment : Fragment() {
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
        reloadList(view)
        (activity as HomeActivity?)?.setFragmentRefreshListener2(object :
            HomeActivity.FragmentRefreshListener {
            override fun onRefresh() {
                reloadList(view)
            }
        }
        )
        return view
    }

    override fun onResume() {
        Log.e("onResume", "onResume")
        val view = view
        super.onResume()
        reloadList(view!!)
    }

    fun reloadList(view: View){
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = PopularRecyclerViewAdapter((activity as HomeActivity).upcomingGames)
                Log.e("PopularGamesFragment", "onCreateView: ${(activity as HomeActivity).upcomingGames}" )
            }
        }
    }
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            UpcomingGamesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}