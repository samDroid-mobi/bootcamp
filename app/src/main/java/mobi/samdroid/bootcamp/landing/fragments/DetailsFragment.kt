package mobi.samdroid.bootcamp.landing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mobi.samdroid.bootcamp.R
import mobi.samdroid.bootcamp.databinding.FragmentDetailsBinding
import mobi.samdroid.bootcamp.landing.viewmodels.DetailsViewModel

class DetailsFragment : BaseFragment() {

    private lateinit var _binding: FragmentDetailsBinding
    private val _viewModel: DetailsViewModel by viewModels()

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

        _viewModel.getDescription()

        setObservers()
    }

    private fun setObservers() {
        _viewModel.liveDescription().observe(viewLifecycleOwner) {
            _binding.textViewDescription.text = it
        }
    }
}