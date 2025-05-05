package mobi.samdroid.bootcamp.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import mobi.samdroid.bootcamp.R
import mobi.samdroid.bootcamp.base.extenions.showToast
import mobi.samdroid.bootcamp.databinding.ActivitySignUpBinding
import mobi.samdroid.bootcamp.landing.MainActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySignUpBinding
    private val _viewModel: SignUpViewModel by viewModels()

    private val _mainLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val username = intent?.getStringExtra(MainActivity.EXTRA_USERNAME) ?: ""
                Toast.makeText(this, getString(R.string.sign_up_successful, username), LENGTH_SHORT)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }

    override fun onStart() {
        super.onStart()

        init()
    }

    private fun init() {
        setListeners()
        setObservers()

        _viewModel.getSavedData(this)
    }

    private fun setListeners() {
        _binding.editTextUsername.addTextChangedListener {
            toggleButton()
        }

        _binding.editTextPassword.addTextChangedListener {
            toggleButton()
        }

        _binding.checkboxRememberMe.setOnCheckedChangeListener { _, isChecked ->
            _viewModel.setRememberMe(this, isChecked)
        }

        _binding.textViewSignup.setOnClickListener {
            if (_viewModel.validateUsername(getUsername())) {
                _viewModel.checkIfUserAlreadyRegistered(applicationContext, getUsername())
            } else {
                showToast(applicationContext, getString(R.string.invalid_username))
            }
        }

        _binding.buttonLogin.setOnClickListener {
            if (_viewModel.validateUsername(getUsername())) {
                _viewModel.checkIfUserFound(applicationContext, getUsername(), getPassword())
            } else {
                showToast(applicationContext, getString(R.string.invalid_username))
            }
        }
    }

    private fun setObservers() {
        _viewModel.liveIsRememberMe().observe(this) {
            _binding.checkboxRememberMe.isChecked = it

            if (it) {
                _viewModel.getCredentials(this)
            }
        }

        _viewModel.liveUsername().observe(this) {
            _binding.editTextUsername.setText(it)
        }

        _viewModel.livePassword().observe(this) {
            _binding.editTextPassword.setText(it)
        }

        _viewModel.liveIsLoggedIn().observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                navigateToLandingScreen()
            }
        }

        _viewModel.liveUser().observe(this) { user ->
            if (user != null) {
                handleSaveData()
                navigateToLandingScreen()
            } else {
                showToast(applicationContext, getString(R.string.user_not_found))
            }
        }

        _viewModel.liveIsUserRegistered().observe(this) { isSuccess ->
            if (isSuccess) {
                handleSaveData()
                navigateToLandingScreen()
            } else {
                showToast(applicationContext, getString(R.string.something_went_wrong))
            }
        }

        _viewModel.liveCheckIfUserRegistered().observe(this) { isUserRegistered ->
            if (isUserRegistered) {
                showToast(applicationContext, getString(R.string.user_already_registered))
            } else {
                _viewModel.registerUser(applicationContext, getUsername(), getPassword())
            }
        }
    }

    private fun toggleButton() {
        val username = getUsername()
        val password = getPassword()

        _binding.buttonLogin.isEnabled = _viewModel.areCredentialsAvailable(username, password)
    }

    private fun navigateToLandingScreen() {
        _viewModel.setLoggedIn(this)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_USERNAME, getUsername())
        intent.putExtra(MainActivity.EXTRA_PASSWORD, getPassword())
        _mainLauncher.launch(intent)
    }

    private fun handleSaveData() {
        _viewModel.saveUsername(this, getUsername())
        _viewModel.savePassword(this, getPassword())
    }

    private fun getUsername() = _binding.editTextUsername.text.toString()
    private fun getPassword() = _binding.editTextPassword.text.toString()
}