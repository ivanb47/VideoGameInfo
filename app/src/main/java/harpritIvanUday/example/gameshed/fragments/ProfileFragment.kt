package harpritIvanUday.example.gameshed.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import harpritIvanUday.example.gameshed.R
import harpritIvanUday.example.gameshed.activities.HomeActivity
import harpritIvanUday.example.gameshed.databinding.FragmentProfileBinding


private lateinit var binding: FragmentProfileBinding
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            binding = FragmentProfileBinding.inflate(layoutInflater)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.rootView.findViewById<View>(R.id.logoutButton).setOnClickListener {
            val fragment = ProfileFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            Firebase.auth.signOut()
            (activity as HomeActivity?)?.moveToLogin()
            transaction.commit()
        }
        return view
    }

}