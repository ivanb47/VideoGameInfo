package harpritIvanUday.example.gameshed.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import harpritIvanUday.example.gameshed.APIFormat
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.activities.HomeActivity
import harpritIvanUday.example.gameshed.adapters.PageAdapter
import harpritIvanUday.example.gameshed.databinding.FragmentHomeBinding
import harpritIvanUday.example.gameshed.databinding.FragmentProfileBinding
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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