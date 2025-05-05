package mobi.samdroid.bootcamp.landing.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.appendPlaceholders
import mobi.samdroid.bootcamp.R
import mobi.samdroid.bootcamp.base.IClickListener
import mobi.samdroid.bootcamp.base.SUser
import mobi.samdroid.bootcamp.base.utils.SUtils
import mobi.samdroid.bootcamp.databinding.FragmentMainBinding
import mobi.samdroid.bootcamp.landing.adapters.UsersAdapter
import mobi.samdroid.bootcamp.landing.viewmodels.MainViewModel

class MainFragment : BaseFragment(), IClickListener {
    private lateinit var _binding: FragmentMainBinding
    private val _viewModel: MainViewModel by viewModels()

    private val mRequestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                _viewModel.user?.let { user ->
                    SUtils.callPhoneNumber(requireContext(), user.mobileNumber)
                }
            }
        }

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

        _viewModel.getUsers(requireContext())

        handleBack()
        setViews()
        setListeners()
        setObservers()
    }

    private fun setViews() {
        _binding.recyclerViewUsers.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            ) // @reverseLayout: to show the data in a reversed order
    }

    private fun setListeners() {

    }

    private fun setObservers() {
        _viewModel.liveUsers().observe(viewLifecycleOwner) { users ->
            _binding.recyclerViewUsers.adapter = UsersAdapter(users).apply {
                inter = this@MainFragment
            }
        }
    }

    private fun handleBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }


    override fun onPhoneClicked(user: SUser) {
        _viewModel.user = user

        if (SUtils.isPermissionGranted(requireContext(), Manifest.permission.CALL_PHONE)) {
            SUtils.callPhoneNumber(requireContext(), "+961 3 943 517")
        } else {
            mRequestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

    override fun onItemClick(user: SUser) {
        findNavController().navigate(R.id.action_MainFragment_to_DetailsFragment)
    }

}