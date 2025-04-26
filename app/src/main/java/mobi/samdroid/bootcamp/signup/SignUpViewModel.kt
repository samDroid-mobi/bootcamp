package mobi.samdroid.bootcamp.signup

import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {
    fun validateUsername(username: String): Boolean {
        return username.contains(".")
    }

    fun areCredentialsAvailable(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}