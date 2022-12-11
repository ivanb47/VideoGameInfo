package harpritIvanUday.example.gameshed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import harpritIvanUday.example.gameshed.*
import harpritIvanUday.example.gameshed.adapters.PageAdapter
import harpritIvanUday.example.gameshed.databinding.ActivityHomeBinding
import harpritIvanUday.example.gameshed.fragments.*
import harpritIvanUday.example.gameshed.viewModel.HomeViewModel
import harpritIvanUday.example.gameshed.viewModel.LoginViewModel
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HomeActivity : AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var fragmentRefreshListener: FragmentRefreshListener? = null
    private var fragmentRefreshListener2: FragmentRefreshListener? = null


    fun moveToLogin() {
        val intent = Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.statusBarColor = this.resources.getColor(R.color.white)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        bottomNavigationView = binding.bottomNavigationView

        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Fragments for bottom tabs
        val homeFragment= HomeFragment()
        val favouriteFragment= FavouriteFragment()
        val aboutUsFragment= AboutUsFragment()
        val profileFragment= ProfileFragment()

        homeViewModel.getFamousGamesData().start()

        homeViewModel.getUpcomingGamesData().start()
        updateUI()
        bottomNavigationView.setOnItemSelectedListener {
            item-> when(item.itemId){
            R.id.home -> {
                setCurrentFragment(homeFragment)
                binding.topAppBar.title = getString(R.string.home)
                true
            }
            R.id.favourite -> {
                setCurrentFragment(favouriteFragment)
                binding.topAppBar.title = getString(R.string.favorites)
                true
            }
            R.id.person -> {
                setCurrentFragment(profileFragment)
                binding.topAppBar.title = getString(R.string.profile)
                true
            }
            R.id.aboutUs -> {
                setCurrentFragment(aboutUsFragment)
                binding.topAppBar.title = getString(R.string.about)
                true
            }
            else -> false
        }
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getFavouriteGames()
        updateFavoriteList()
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.viewPager,fragment)
            commit()
        }





    private fun updateFavoriteList(){
        runOnUiThread {
            kotlin.run {
                if(getFragmentRefreshListener()!= null){
                    getFragmentRefreshListener()?.onRefresh()
                }
            }
        }
    }
    private fun updateUI(){
        runOnUiThread{
            kotlin.run {
                if(getFragmentRefreshListener()!= null){
                    getFragmentRefreshListener()?.onRefresh()
                }
            }
        }
    }
    private fun updateFavUI(){
        runOnUiThread{
            kotlin.run {
                if(getFragmentRefreshListener2()!= null){
                    getFragmentRefreshListener2()?.onRefresh()
                }
            }
        }
    }

    private fun getFragmentRefreshListener(): FragmentRefreshListener? {
        return fragmentRefreshListener
    }

    fun setFragmentRefreshListener(fragmentRefreshListener1: FragmentRefreshListener) {
        fragmentRefreshListener = fragmentRefreshListener1
    }

    interface FragmentRefreshListener {
        fun onRefresh()
    }

    private fun getFragmentRefreshListener2(): FragmentRefreshListener? {
        return fragmentRefreshListener2
    }

    fun setFragmentRefreshListener2(fragmentRefreshListener1: FragmentRefreshListener) {
        fragmentRefreshListener2 = fragmentRefreshListener1
    }

    interface FragmentRefreshListener2 {
        fun onRefresh()
    }
}