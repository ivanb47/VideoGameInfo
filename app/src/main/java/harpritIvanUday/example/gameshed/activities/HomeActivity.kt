package harpritIvanUday.example.gameshed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HomeActivity : AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView

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

        // Fragments for bottom tabs
        val homeFragment= HomeFragment()
        val favouriteFragment= FavouriteFragment()
        val aboutUsFragment= AboutUsFragment()
        val profileFragment= ProfileFragment()

        getFamousGameDataActivity()
        homeViewModel.getUpcomingGamesData().start()
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

//    override fun onResume() {
//        super.onResume()
//        homeViewModel.getFavouriteGames()
//    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.viewPager,fragment)
            commit()
        }

    fun getFamousGameDataActivity(){
        homeViewModel.getFamousGamesData().start()
    }

}