package mobi.samdroid.bootcamp.landing

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import mobi.samdroid.bootcamp.R
import mobi.samdroid.bootcamp.databinding.ActivityMainBinding
import mobi.samdroid.bootcamp.landing.viewmodels.LandingViewModel
import mobi.samdroid.bootcamp.signup.SignUpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private val _viewModel: LandingViewModel by viewModels()

    private lateinit var _navController: NavController
    private var _username = ""
    private var _password = ""

    companion object {
        const val EXTRA_USERNAME = "username"
        const val EXTRA_PASSWORD = "password"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }

    override fun onStart() {
        super.onStart()

        _viewModel.getSavedData(this)

        setViews()
        setListeners()
        setObservers()
    }

    private fun setViews() {
        _binding.textViewTitle.text = getString(R.string.title_hello, _username, _password)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        // Get the NavController
        _navController = navHostFragment.navController
    }

    private fun setObservers() {
        _viewModel.liveUsername().observe(this) { username ->
            _username = username
            _binding.textViewTitle.text = getString(R.string.title_hello, _username, _password)
        }

        _viewModel.livePassword().observe(this) { password ->
            _password = password
            _binding.textViewTitle.text = getString(R.string.title_hello, _username, _password)
        }


        _viewModel.liveLogout().observe(this) { isLoggedOut ->
            if (isLoggedOut) {
                val intent = Intent(this, SignUpActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setListeners() {
        _binding.textViewLogout.setOnClickListener {
            _viewModel.logout(this)
        }
    }
}