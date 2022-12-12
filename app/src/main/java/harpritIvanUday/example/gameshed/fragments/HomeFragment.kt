package harpritIvanUday.example.gameshed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.adapters.PageAdapter

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val viewPager = view.findViewById<ViewPager>(R.id.viewPagerHome)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout2)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = PageAdapter(childFragmentManager)
        return view
    }

}