package harpritIvanUday.example.gameshed.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import harpritIvanUday.example.gameshed.fragments.PopularGamesFragment
import harpritIvanUday.example.gameshed.fragments.UpcomingGamesFragment

class PageAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                PopularGamesFragment()
            }
            1 -> {
                UpcomingGamesFragment()
            }
            else -> {
                PopularGamesFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Popular Games"
            }
            1 -> {
                return "Upcoming Games"
            }
        }
        return super.getPageTitle(position)
    }

}