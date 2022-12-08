package harpritIvanUday.example.gameshed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.activities.HomeActivity
import harpritIvanUday.example.gameshed.databinding.FragmentProfileBinding


private lateinit var binding: FragmentProfileBinding
private lateinit var auth: FirebaseAuth
class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            (activity as HomeActivity?)?.moveToLogin()
        }
        binding.profileEmailText.text = Firebase.auth.currentUser?.email
        binding.profileNameText.text = Firebase.auth.currentUser?.displayName
        return binding.root
    }

}