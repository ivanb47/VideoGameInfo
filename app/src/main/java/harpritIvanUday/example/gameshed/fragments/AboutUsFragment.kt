package harpritIvanUday.example.gameshed.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import harpritIvanUday.example.gameshed.R

class AboutUsFragment : Fragment() {
    private lateinit var info1 : TextView
    private lateinit var info2: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_about_us, container, false)
        info1 = view.findViewById(R.id.info1)
        info2 = view.findViewById(R.id.info2)

        info1.setText(R.string.app_info)
        var info = ""
        var count = 0
        resources.getStringArray(R.array.app_features).forEach {
            if(count == 0 ){
                info = it
            }
            else{
                info += "\n\n"+ it
            }
            count ++
        }
        info2.text = info
        return view
    }

}