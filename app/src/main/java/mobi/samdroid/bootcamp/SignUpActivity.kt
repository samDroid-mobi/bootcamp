package mobi.samdroid.bootcamp

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import mobi.samdroid.bootcamp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySignUpBinding

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
            if(isChecked) {
                Toast.makeText(applicationContext, "You will be remembered!", LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "You will not be remembered!", LENGTH_SHORT).show()
            }
        }

        _binding.buttonLogin.setOnClickListener {
            if(validateUsername()) {
                Toast.makeText(applicationContext, "Login successful!", LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Invalid username!", LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleButton() {
        val username = _binding.editTextUsername.text.toString()
        val password = _binding.editTextPassword.text.toString()

        _binding.buttonLogin.isEnabled = username.isNotEmpty() && password.isNotEmpty()
    }

    private fun validateUsername(): Boolean {
        val username = _binding.editTextUsername.text.toString()
        return username.contains(".")
    }
}