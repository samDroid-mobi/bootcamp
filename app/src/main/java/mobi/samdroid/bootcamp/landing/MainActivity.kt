package mobi.samdroid.bootcamp.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import mobi.samdroid.bootcamp.R
import mobi.samdroid.bootcamp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
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

        getExtras()
        setViews()
        setListeners()
    }

    private fun setViews() {
        _binding.textViewTitle.text = getString(R.string.title_hello, _username, _password)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        // Get the NavController
        _navController = navHostFragment.navController
    }

    private fun getExtras() {
        _username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        _password = intent.getStringExtra(EXTRA_PASSWORD) ?: ""

    }

    private fun setListeners() {
        _binding.textViewTitle.setOnClickListener {
            val intent = Intent()
            intent.putExtra(EXTRA_USERNAME, _username)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        findNavController(R.id.fragment_nav_host).popBackStack()
    }
}