package mobi.samdroid.bootcamp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import mobi.samdroid.bootcamp.databinding.ActivitySignUpBinding
import mobi.samdroid.bootcamp.landing.MainActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySignUpBinding

    private val _mainLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                val username = intent?.getStringExtra(MainActivity.EXTRA_USERNAME) ?: ""
                Toast.makeText(this, getString(R.string.sign_up_successful, username), LENGTH_SHORT).show()
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
    }

    private fun setListeners() {
        _binding.editTextUsername.addTextChangedListener {
            toggleButton()
        }

        _binding.editTextPassword.addTextChangedListener {
            toggleButton()
        }

        _binding.checkboxRememberMe.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "You will be remembered!", LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "You will not be remembered!", LENGTH_SHORT)
                    .show()
            }
        }

        _binding.buttonLogin.setOnClickListener {
            if (validateUsername()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_USERNAME, getUsername())
                intent.putExtra(MainActivity.EXTRA_PASSWORD, getPassword())
                _mainLauncher.launch(intent)
            } else {
                Toast.makeText(applicationContext, "Invalid username!", LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleButton() {
        val username = getUsername()
        val password = getPassword()

        _binding.buttonLogin.isEnabled = username.isNotEmpty() && password.isNotEmpty()
    }

    private fun validateUsername(): Boolean {
        val username = getUsername()
        return username.contains(".")
    }

    private fun getUsername() = _binding.editTextUsername.text.toString()
    private fun getPassword() = _binding.editTextPassword.text.toString()
}