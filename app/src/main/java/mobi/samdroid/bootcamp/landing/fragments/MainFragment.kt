package mobi.samdroid.bootcamp.landing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mobi.samdroid.bootcamp.R
import mobi.samdroid.bootcamp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var _binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onStart() {
        super.onStart()

        setListeners()
    }

    private fun setListeners() {
        _binding.textViewTitle.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_DetailsFragment)
        }
    }

}