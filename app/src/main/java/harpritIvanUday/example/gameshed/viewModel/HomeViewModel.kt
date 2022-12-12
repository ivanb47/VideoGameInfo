package harpritIvanUday.example.gameshed.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import harpritIvanUday.example.gameshed.APIFormat
import harpritIvanUday.example.gameshed.Results
import harpritIvanUday.example.gameshed.activities.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HomeViewModel: ViewModel() {

    var popularGames: List<Results> = mutableListOf()
    var upcomingGames: List<Results> = mutableListOf()
    var favoriteGames: List<Results> = mutableListOf()
    val currentUser = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().uid.toString())

    var popularGames_ = MutableLiveData<List<Results>>()
    var upcomingGames_ = MutableLiveData<List<Results>>()
    var favoriteGames_ = MutableLiveData<List<Results>>()

    fun getFavouriteGames(){
        Log.e("getFavouriteGames", "called")
        favoriteGames = mutableListOf()
        favoriteGames_ = MutableLiveData<List<Results>>()
        currentUser.get().addOnSuccessListener { it ->
            @Suppress("UNCHECKED_CAST") val result = it.get("favorites") as List<Int>?
            result?.forEach{ id ->
                getGameData(id).start()
            }
        }
    }
     fun getFamousGamesData(): Thread
    {
        return Thread {
            val uri = "https://api.rawg.io/api/games?key=d64de3cb496f46a1a5b5f3b1669764e9"
            val url = URL(uri)
            val connection  = url.openConnection() as HttpsURLConnection
            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, APIFormat::class.java)
                popularGames = request.results
                popularGames_.postValue( request.results)
                // update...
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                Log.e("API", "Error")
            }
        }
    }
     fun getUpcomingGamesData(): Thread
    {
        return Thread {
            val uri = "https://api.rawg.io/api/games?key=d64de3cb496f46a1a5b5f3b1669764e9&dates=2023-01-01,2023-09-30"
            val url = URL(uri)
            val connection  = url.openConnection() as HttpsURLConnection
            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, APIFormat::class.java)
                upcomingGames = request.results
                upcomingGames_.postValue( request.results)

                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                Log.e("API", "Error")
            }
        }
    }
     fun getGameData(id: Int): Thread
    {
        return Thread {
            val uri = "https://api.rawg.io/api/games/"
            val key = "d64de3cb496f46a1a5b5f3b1669764e9"
            val url = URL("$uri$id?key=$key")
            val connection  = url.openConnection() as HttpsURLConnection
            if(connection.responseCode == 200)
            {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Results::class.java)
                favoriteGames = favoriteGames + request
                favoriteGames_.postValue( favoriteGames + request)
                inputStreamReader.close()
                inputSystem.close()
            }
            else
            {
                Log.e("API", "Error")
            }
        }
    }

}