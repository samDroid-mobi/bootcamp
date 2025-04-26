package mobi.samdroid.bootcamp.landing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import mobi.samdroid.bootcamp.R
import mobi.samdroid.bootcamp.databinding.FragmentDetailsBinding

class DetailsFragment : BaseFragment() {

    private lateinit var _binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onStart() {
        super.onStart()

        setListeners()
    }

    private fun setListeners() {
        _binding.textViewTitle.setOnClickListener {
            findNavController().popBackStack(R.id.DetailsFragment, true)
        }
    }
}