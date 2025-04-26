package mobi.samdroid.bootcamp.landing.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

        handleBack()
        setViews()
        setListeners()
    }

    private fun setViews() {
        _binding.recyclerViewUsers.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            ) // @reverseLayout: to show the data in a reversed order

        _binding.recyclerViewUsers.adapter = UsersAdapter(_viewModel.getUsers()).apply {
            inter = this@MainFragment
        }
    }

    private fun setListeners() {

    }

    private fun handleBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }


    override fun onClick(user: SUser) {
        _viewModel.user = user

        if (SUtils.isPermissionGranted(requireContext(), Manifest.permission.CALL_PHONE)) {
            SUtils.callPhoneNumber(requireContext(), user.mobileNumber)
        } else {
            mRequestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

}