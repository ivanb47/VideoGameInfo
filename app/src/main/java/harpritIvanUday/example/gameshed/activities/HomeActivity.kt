package harpritIvanUday.example.gameshed.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import harpritIvanUday.example.gameshed.APIFormat
import harpritIvanUday.example.gameshed.OnGameClickListener
import harpritIvanUday.example.gameshed.adapters.PageAdapter
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.Results
import harpritIvanUday.example.gameshed.databinding.ActivityHomeBinding
import harpritIvanUday.example.gameshed.fragments.*
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var fragmentRefreshListener: FragmentRefreshListener? = null
    var popularGames: List<Results> = mutableListOf()

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

        // Fragments for bottom tabs
        val homeFragment= HomeFragment()
        val favouriteFragment= FavouriteFragment()
        val aboutUsFragment= AboutUsFragment()
        val profileFragment= ProfileFragment()

        getGamesData().start()

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
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.viewPager,fragment)
            commit()
        }

    private fun getBookData(): Thread
    {
        return Thread {
            val uri = "https://api.nytimes.com/svc/topstories/v2/"
            val type = "arts"
            val apiKey = "4VoThQ8geeevCgCdjKhBnvo3G8qvPAGz"
            val url = URL("$uri$type.json?api-key=$apiKey")
            val connection  = url.openConnection() as HttpsURLConnection
            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, APIFormat::class.java)
                Log.d("API", request.toString())
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
               Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getGamesData(): Thread
    {
        return Thread {
            val uri = "https://api.rawg.io/api/games?key=d64de3cb496f46a1a5b5f3b1669764e9"
            val url = URL("$uri")
            val connection  = url.openConnection() as HttpsURLConnection
            Log.d("API",connection.inputStream.toString())
            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, APIFormat::class.java)
                Log.d("API", request.toString())
                popularGames = request.results
                updateUI(request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
             Log.e("API", "Error")
            }
        }
    }
    private fun updateUI( request: APIFormat){
        runOnUiThread{
            kotlin.run {
                if(getFragmentRefreshListener()!= null){
                    getFragmentRefreshListener()?.onRefresh();
                }
            }
        }
    }

    fun getFragmentRefreshListener(): FragmentRefreshListener? {
        return fragmentRefreshListener
    }

    fun setFragmentRefreshListener(fragmentRefreshListener1: FragmentRefreshListener) {
        fragmentRefreshListener = fragmentRefreshListener1
    }

    interface FragmentRefreshListener {
        fun onRefresh()
    }

}